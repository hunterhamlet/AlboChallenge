# Assignment Android Dev

## Preguntas Teóricas

**Explique los principios de la arquitectura MVVM. ¿Por qué es preferible sobre MVC en
desarrollo Android?**

R:

**Describa cómo Koin facilita la inyección de dependencia en aplicaciones Android.**

R:

**Compare Retrofit y Volley para realizar llamadas de red en Android. ¿Cuáles son las
ventajas de usar uno sobre el otro?**

R:

## Ejercicios Prácticos con Código:

**Aclaración**: Los ejericios practicos se haran un poco en desorden, esto por que me sirve a mi para ilustrar digamos el proceso que sigo para crear un cliente, inyectar dependencias, y consumir los servicios, espero no haya problema alguno.

### **Uso de Retrofit**

#### **Crear BASE_URL en archivo Gradle**

Para Mayor practicidad, creo un BASE_URL, para poder ser consumido en el cliente, este generalmente no cambia, sin embargo en ocaciones es probable que tengamos distintos BASE_URL.

```kotlin
// Build Config fields
buildConfigField("String", "BASE_URL", "base_url_to_consume_apis")
```

#### **Crear cliente generico con URL base o URL Custom**

Con este bloque de código podemos crear un cliente generico para el uso de los servicios REST, podemos tener una Base URL por default, o en dado caso crear un cliente con una url Custom. Si es la URL default, lo que hacemos es guardarla en el archivo Gradle y la importamos con **BuildConfig** y en este caso el nombre que le hayamos puesto; en este caso se llama **BASE_URL**.

```kotlin
object Client {

    private val okHttpClientInterceptorRequest by lazy {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(okHttpClientInterceptorRequest)
            .build()
    }

    inline fun <reified T> getServices(host: String = BuildConfig.BASE_URL): T {
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(host).build().create(T::class.java)
    }
}
```

#### **Crear las URL a consumir**

Para las URL de los servicios con BASE_URL que estamos configurando, genero un archivo con los distintos servicios a consumir.

```kotlin
const val CATEGORIES = "api/json/v1/1/categories.php"
const val MEALS_BY_CATEGORY = "api/json/v1/1/filter.php"
const val MEAL_DETAILS = "api/json/v1/1/lookup.php"
const val SEARCH_BY = "api/json/v1/1/search.php"
const val RANDOM_MEAL = "api/json/v1/1/random.php"
```

#### **Crear los servicios a consumir**

Por ultimo creo las interfaces para poder ser utilizadas en el Cliente.

```kotlin
interface MealServices {

    @GET(CATEGORIES)
    suspend fun getCategories(): Response<CategoriesResponse>

    @GET(MEALS_BY_CATEGORY)
    suspend fun getMealsByCategory(@QueryMap query: Map<String, String>): Response<MealsByCategoryResponse>

    @GET(MEAL_DETAILS)
    suspend fun getMealDetails(@QueryMap query: Map<String, String>): Response<MealDetailResponse>

    @GET(SEARCH_BY)
    suspend fun getMealsByQuery(@QueryMap query: Map<String, String>)

    @GET(RANDOM_MEAL)
    suspend fun getRandomMeal(): Response<MealDetailResponse>
}
```

### **Inyección de Dependencia**

La inyeccion de dependencias es muy sencilla, al menos con Koin, ya que solo es cosa de agregarla a un módulo. Antes de eso, lleva un proceso completo, como por ejemplo inicializar Koin, agregar los modulos a inyectar, etc.

#### **Inicializar Koin**

Para inicializar Koin ocuparemos el archivo de configuración de la aplicación, en este caso debe de extender de **Application** o **MultiDexApplication**. En el **OnCreate**, debemos inicializar Koin como se muestra a continuacion.
```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                listOf(
                    client, datasources, repositories, viewModels, useCases
                )
            )
        }
    }

}
```

Dentro de **starKoin** agregaremos la lista de modulos que ocuparemos paa inyectar.

#### **Archivo Modules**

En mi caso, genero un archivo de nombre **Modules** el cual contiene los modulos que se pueden inyectar y ocupar.

```kotlin
val client = module { }

val datasources = module { }

val repositories = module { }

val useCases = module { }

val viewModels = module { }
```

#### **Injeccion de dependencias**

Para inyectar alguna dependencia, podemos hacerlo de dos maneras, la primera es en el constructor, como se muestra a continuacion.

```kotlin
val datasources = module {
    single { RickAndMortyDatasourceImpl(get(), get(named(DEFERRED))) as RickAndMortyDatasource }
}
```

Arriba podemos ver que estamos inyectando con el metodo ```get()``` lo necesario en el constructor. La otra manera seria directamente por ejemplo en la vista, cuando ocupamos un ```ViewModel``` dentro de una ```activity``` o ```fragment```, como se muestra a continuacion:

```kotlin
// Inject viewModel
private val viewModel: MainViewModel by viewModel()

// Inject anything
private val datasource: RickAndMortyRepository by inject()

// Inject shared viewModel
private val viewModelShared by activityViewModel<MainViewModel>()

// Use in flow into nav graph
val mainViewModel: MainViewModel by koinNavGraphViewModel(R.id.my_graph)

```

Para inyectar algun usescase, repository, etc en el viewModel lo podemos realizar en el constructor, como se muestra acontinuacion:

```kotlin
class MainViewModel(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getCharacterDeferredUseCase: GetCharacterDeferredUseCase
) : ViewModel() {

}
```

### **ViewModel y LiveData**

Para trabajar con ```liveData``` en el ```viewModel``` y ver los resultados en el ```activity``` o ```fragment```, tengo varias formas de trabajarlo:


Crear los observables en el ```viewModel```, como se muestra a continuación:
```kotlin
class MainViewModel(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getCharacterDeferredUseCase: GetCharacterDeferredUseCase
) : ViewModel() {

    // Normal

    private val _characterObserver = MutableLiveData<MutableList<RickAndMortyCharacter>>()
    val characterObserver: LiveData<MutableList<RickAndMortyCharacter>> get() = _characterObserver

    // Deferred
    private val _characterDeferredObserver = MutableLiveData<MutableList<RickAndMortyCharacter>>()
    val characterDeferredObserver: LiveData<MutableList<RickAndMortyCharacter>> get() = _characterObserver

    // Await
    private val _characterAwaitObserver = MutableLiveData<MutableList<RickAndMortyCharacter>>()
    val characterAwaitObserver: LiveData<MutableList<RickAndMortyCharacter>> get() = _characterObserver
    
    }
```

O por medio de un state, que contiene distintos estados y un generico de datos como se muestra abajo:

Clase states:

```kotlin 
sealed class UIStates<out T: Any> {
    object Init : UIStates<Nothing>()
    object Loading : UIStates<Nothing>()
    class Success<T: Any>(val value: T?) : UIStates<T>()
    class Error(val message: String) : UIStates<Nothing>()
}

```

Observables
```kotlin 
private val _uiState: MutableLiveData<UIStates<String>> =
        MutableLiveData<UIStates<String>>().apply { value = UIStates.Init }
    val uiState: LiveData<UIStates<String>> get() = _uiState
```

Y de lado de la vista queda de la siguiente manera:
```kotlin
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupObservables()
        setupRequest()

    }

    private fun setupRequest() {
        mainViewModel.apply {
            getCharacters()
            getAwaitCharacters()
            getDeferredCharacters()
        }
    }

    private fun setupObservables() {
        mainViewModel.apply {
            characterObserver.observe(this@MainActivity) {

            }
            characterAwaitObserver.observe(this@MainActivity) {
                
            }
            characterDeferredObserver.observe(this@MainActivity) {
                
            }
        }
    }

}
```
Aqui en la vista ya podriamos ocupar los datos obtenidos, cabe destacar que para obtener las peticiones de datos, al menos conozco, tres maneras:

La primera seria de manera normal:
```kotlin
fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCharacterUseCase.invoke()
            _characterObserver.postValue(result)
        }
    }
```

La segunda es esperar hasta que el servicio regrese los datos, con ```async``` y ```await```:
```kotlin
fun getAwaitCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            val resultAsync = async { getCharacterUseCase.invoke() }
            val result = resultAsync.await()
            _characterAwaitObserver.postValue(result)
        }
    }
```

Por último es similar a la anterior, solo que se trabaja desde el datasource, pero funciona basicamente igual, esperar hasta que respondan los datos, aqui solo ocupamos el ```await```:

Datasource
```kotlin
override suspend fun getCharactersDeferred(): MutableList<RickAndMortyCharacterResponse> {
        runCatching {
            val result =
                clientDeferred.getServices<RickAndMortyServices>().getCharactersCoroutines().await()
            return result.results?.filterNotNull()?.toMutableList() ?: mutableListOf()
        }.getOrElse {
            return mutableListOf()
        }
    }
```

ViewModel
```kotlin
fun getDeferredCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getCharacterDeferredUseCase.invoke()
            _characterDeferredObserver.postValue(result)
        }
    }
```
**Nota**: Para mas información acerca de como se implementa, puedes ir al siguiente link.

#### **Ejemplo de UI**

## Preguntas de Resolución de Problemas


## Caso de Estudio