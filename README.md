# Assignment Android Dev

## Preguntas Teóricas

**Explique los principios de la arquitectura MVVM. ¿Por qué es preferible sobre MVC en
desarrollo Android?**

R: A mi ver, la arquitectura MVVM, cuenta con muchas ventajas sobre MVC, nos muestra que por ejemplo; las clases se vuelven mas simples de entender.
Tambien existen clases qeu delegan de mucha responsabilidad a la vista, por ejemplo em MVC generalmente cargamos todo, las peticiones, la logica, las conversiones, pero en MVVM, tenemos clases que tienen su funcion especifica, esto delega de responsabilidad a la vista. Por ejemplo si lo conbinamos con MVI y Clean, tenemos los ```datasource```, encargados de la fuente de datos, los ```repositories``` que son los encargados, al menos yo los considero asi, de pasar los datos de la capa de ```data``` a los datos del ```dominio```, por ultimo tenemos los ```usecases```, que son los encargados de tener una unica responsabilidad. Esto sultimos son los encargados de inyectarse a los ```viewModels```, y el dentro de este ultimo podemos convertir, darle formato, etc; dejarlo listos para que la vista lo tome y lo muestre.

**Describa cómo Koin facilita la inyección de dependencia en aplicaciones Android.**

R: Koin es una libreria que nos ayuda a la inyección de dependencias, pero lo que lo hace mas fácil, es que la curva de aprendizaje es muy rapida, al principio puede que te cueste trabajo, sin embargo, conforme avanzas, se vuelve mas fluido, ademas cuenta con ```funciones``` que nos facilita el uso, por ejemplo, ```inject()```, ```viewModel()```, ```activityViewModel()```. Ademas la inyeccion es sencilla, lo haces desde el constructor, o directamente con la funcion ```inject```().

**Compare Retrofit y Volley para realizar llamadas de red en Android. ¿Cuáles son las
ventajas de usar uno sobre el otro?**

R: En mi caso no he ocupado volley, sin embargo puedo decir que al ver la documentación, ```Volley``` se me hace un poco mas complejo, debido a que tienes que configurar el cache, el tipo de petición, el requestClient (por asi decirlo) y luego ya realizar el request. A diferencia de ```Retrofit```, que solo creas un client y este puede ser utilizado con las distintos servicios a ocupar. A mi parecer, volley es para ser ocupado en una arquitectura como MVC, la cual haces peticiones en la vista, y Retrofit es mas para aplicaciones con arquitecturas mas robustas, se puede inyectar, se puede realizar un logging, etc, tiene adaptadores para parsear los datos en automatico,.

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

En este caso agregare un custom view donde podremos ver el componente en xml:
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:paddingHorizontal="16dp"
    android:paddingVertical="8dp">

    <TextView
        android:id="@+id/tv_actual_count"
        android:text="0"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="4dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/container_bar" />

    <TextView
        android:id="@+id/tv_limit_count"
        android:text="25/25 tarjetazos"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="@id/tv_actual_count"
        app:layout_constraintBottom_toBottomOf="@id/tv_actual_count"/>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/container_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toTopOf="parent">

        <View
            android:id="@+id/count_one"
            android:layout_width="0dp"
            android:layout_height="10dp"
            android:background="@drawable/view_rounded"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toStartOf="@id/count_two"
            android:backgroundTint="@android:color/holo_blue_bright"/>

        <View
            android:id="@+id/divider_one"
            android:layout_width="2dp"
            android:layout_height="0dp"
            app:layout_constraintTop_toTopOf="@id/count_one"
            app:layout_constraintBottom_toBottomOf="@id/count_one"
            app:layout_constraintStart_toEndOf="@id/count_one"
            app:layout_constraintEnd_toStartOf="@id/count_two"
            android:background="@android:color/darker_gray"/>

        <View
            android:id="@+id/count_two"
            android:layout_width="0dp"
            android:layout_height="10dp"
            app:layout_constraintTop_toTopOf="@id/count_one"
            app:layout_constraintBottom_toBottomOf="@id/count_one"
            app:layout_constraintStart_toEndOf="@id/count_one"
            app:layout_constraintEnd_toStartOf="@id/count_three"
            android:background="@android:color/holo_blue_bright"/>

        <View
            android:id="@+id/divider_two"
            android:layout_width="2dp"
            android:layout_height="0dp"
            app:layout_constraintTop_toTopOf="@id/count_one"
            app:layout_constraintBottom_toBottomOf="@id/count_one"
            app:layout_constraintStart_toEndOf="@id/count_two"
            app:layout_constraintEnd_toStartOf="@id/count_three"
            android:background="@android:color/darker_gray"/>

        <View
            android:id="@+id/count_three"
            android:layout_width="0dp"
            android:layout_height="10dp"
            app:layout_constraintTop_toTopOf="@id/count_one"
            app:layout_constraintBottom_toBottomOf="@id/count_one"
            app:layout_constraintStart_toEndOf="@id/count_two"
            app:layout_constraintEnd_toStartOf="@id/count_four"
            android:background="@android:color/holo_blue_bright"/>

        <View
            android:id="@+id/divider_three"
            android:layout_width="2dp"
            android:layout_height="0dp"
            app:layout_constraintTop_toTopOf="@id/count_one"
            app:layout_constraintBottom_toBottomOf="@id/count_one"
            app:layout_constraintStart_toEndOf="@id/count_three"
            app:layout_constraintEnd_toStartOf="@id/count_four"
            android:background="@android:color/darker_gray"/>

        <View
            android:id="@+id/count_four"
            android:layout_width="0dp"
            android:layout_height="10dp"
            app:layout_constraintTop_toTopOf="@id/count_one"
            app:layout_constraintBottom_toBottomOf="@id/count_one"
            app:layout_constraintStart_toEndOf="@id/count_three"
            app:layout_constraintEnd_toStartOf="@id/count_five"
            android:background="@android:color/holo_blue_bright"/>

        <View
            android:id="@+id/divider_four"
            android:layout_width="2dp"
            android:layout_height="0dp"
            app:layout_constraintTop_toTopOf="@id/count_one"
            app:layout_constraintBottom_toBottomOf="@id/count_one"
            app:layout_constraintStart_toEndOf="@id/count_four"
            app:layout_constraintEnd_toStartOf="@id/count_five"
            android:background="@android:color/darker_gray"/>

        <View
            android:id="@+id/count_five"
            android:layout_width="0dp"
            android:layout_height="10dp"
            android:rotation="180"
            app:layout_constraintTop_toTopOf="@id/count_one"
            app:layout_constraintBottom_toBottomOf="@id/count_one"
            app:layout_constraintStart_toEndOf="@id/count_four"
            app:layout_constraintEnd_toEndOf="parent"
            android:background="@drawable/view_rounded"
            android:backgroundTint="@android:color/holo_blue_bright"/>


    </androidx.constraintlayout.widget.ConstraintLayout>


</androidx.constraintlayout.widget.ConstraintLayout>
```

Y posteriormente la clase encargada de darle superpoderes:
```kotlin
class CountViewCustom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: CustomViewCountBinding =
        CustomViewCountBinding.inflate(LayoutInflater.from(context), this, true)

    private val listPainView = mutableListOf(
        binding.countOne,
        binding.countTwo,
        binding.countThree,
        binding.countFour,
        binding.countFive
    )

    private var actualCount: Int = 0
    private var maxCount: Int = 0

    fun setupCounter(actualCount: Int, maxCount: Int) {
        this.actualCount = actualCount
        this.maxCount = maxCount
        binding.tvLimitCount.text = "${actualCount}/${maxCount} tarjetazos"
        checkActiveSegments()
    }

    fun updateSegments(updateValue: Int){
        this.actualCount = updateValue
        checkActiveSegments()
    }

    private fun checkActiveSegments() {
        val result = (actualCount * 100) / maxCount

        when{
            result >= 20 && result < 40 -> {
                listPainView[0].active()
                listPainView[1].disable()
                listPainView[2].disable()
                listPainView[3].disable()
                listPainView[4].disable()
            }
            result >= 40 && result < 60 -> {
                listPainView[0].active()
                listPainView[1].active()
                listPainView[2].disable()
                listPainView[3].disable()
                listPainView[4].disable()
            }
            result >= 60 && result < 80 -> {
                listPainView[0].active()
                listPainView[1].active()
                listPainView[2].active()
                listPainView[3].disable()
                listPainView[4].disable()
            }
            result >= 80 && result < 100 -> {
                listPainView[0].active()
                listPainView[1].active()
                listPainView[2].active()
                listPainView[3].active()
                listPainView[4].disable()
            }
            result >= 100 -> {
                listPainView[0].active()
                listPainView[1].active()
                listPainView[2].active()
                listPainView[3].active()
                listPainView[4].active()
            }
            else -> {
                listPainView[0].disable()
                listPainView[1].disable()
                listPainView[2].disable()
                listPainView[3].disable()
                listPainView[4].disable()
            }

        }
    }

    private fun View.active() {
        this.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, android.R.color.holo_blue_bright))
    }

    private fun View.disable() {
        this.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, android.R.color.holo_red_dark))
    }


}
```

Por ultimo se muestra la implementación en el activity y una imagen de como se ve:

XML
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".features.exercises_practice_code.presentation.screens.MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <com.hamon.albochallengenormal.components.CountViewCustom
        android:id="@+id/custom_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:translationZ="5dp"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

Activity
```kotlin
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.customView.setupCounter(15, 25)
    }
}
```

Imagen de implementación

TODO: Agregar la imagen aqui

###### Componente en compose

```kotlin
@Composable
fun CountCustom(actualCount: Int, maxCount: Int) {

    val result = (actualCount * 100) / maxCount

    Column {
        Row(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weight = 1f, fill = true),
                color = if (result > 0) Color.Blue else Color.Red,
                shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
            ) {

            }
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(),
                color = Color.White
            )
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weight = 1f, fill = true),
                color = if (result > 20) Color.Blue else Color.Red,
            ) {

            }
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(),
                color = Color.White
            )
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weight = 1f, fill = true),
                color = if (result > 40) Color.Blue else Color.Red,
            ) {

            }
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(),
                color = Color.White
            )
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weight = 1f, fill = true),
                color = if (result > 60) Color.Blue else Color.Red,
            ) {

            }
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight(),
                color = Color.White
            )
            Surface(
                modifier = Modifier
                    .weight(weight = 1f, fill = true)
                    .fillMaxHeight(), color = if (result > 80) Color.Blue else Color.Red,
                shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)
            ) {

            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "0")
            Surface(modifier = Modifier.weight(1f)) {

            }
            Text(text = "0/25 tarjetazos")
        }
    }
}
```

Para intergarlo en una pantalla se realiza de la siguiente manera:
```kotlin
@Composable
fun CodeExercisesScreen() {
    CountCustom(actualCount = 5, maxCount = 25 )
}
```

## Preguntas de Resolución de Problemas
#### **Dada una situación donde una aplicación Android se enfrenta a problemas de memoria debido al manejo incorrecto de contextos, ¿cómo lo solucionaría?**

R: Actualmente, no me ha tocado resolver problemas de memoria, por contextos, mas bien me toco un problema de memoria, pero por que teniamos una sola actividad para manejar todos los flujos, no esta mal, sin embargo; pasabamos datos atravez de todos los flujos, he ibamos recolectando un bundle, con estos datos, entonces, si por ejemplo tenias varios datos recolectados de distintos flujos, y tenias un objeto a guardar muy grande, esto ocacionaba de la app muriera. La manera en la que propuse resolver esto fue que cada flujo tuviera su actividad propia, es decir, tuvieramos el home con una actividad, y cuando iniciaramos un flujo, mandaramos un intent para iniciar una actividad, donde viviera todo el flujo, navGraph, fragments, viewModel compartido etc, esta propuesta soluciono el problema. En otro caso por ejemplo, teniamos problemas con el back, por que haciamos lo mismo, almacenabamos como tal el objeto ```Parcelable``` o ```Serializable```, y esto rompia la app, una manera de solicionarlo fue pasar como tal, el objeto ```JSON```, como un string, asi no manejabamos parcelables solo serializabamos y deserializabamos a nuestra manera con ``JSON``.

#### **Si una aplicación experimenta retardos debido a operaciones de red en el hilo principal, ¿cómo optimizaría el código?**

R: Generalment, no ocupo el hilo principal para las peticiones. Lo que ocupo es un ```scope``` en el ```viewModel```, y dentro un ```Dispatcher.IO```, con esto evito problemas con el hilo principal, ahora para mostrar los datos, ocupo ```postValue()```, para poder brindar los datos a mostrar al ```LiveData```. Para recoger los datos, podemos ocupar ```RxJava``` o ```Flows```.

## Caso de Estudio