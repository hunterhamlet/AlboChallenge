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

**ViewModel y LiveData**

**Inyección de Dependencia**

**Uso de Retrofit**

### **Crear cliente generico con URL base o URL Custom**

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

    inline fun <reified T> getServices(host: String): T {
        return Retrofit.Builder().client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(host).build().create(T::class.java)
    }
}
```


**Ejemplo de UI**

## Preguntas de Resolución de Problemas


## Caso de Estudio