# Desafio, iteração 2
###### por raphael.bernardo:

### **Novidades** ###

- __Principais mudanças__:
  * __MVP__ - Model View Presenter: Separa as classes de modo em que cada uma dessas entidades trabalhem com um conjunto de responsablidades similares (neste caso, __Model__ sabe guardar e até processar dados em memória, __View__: sabe como separar e organizar os dados da Model nas telas, __Presenter__: sabe orquestrar chamadas de obtenção de dados e repassa-los para a view de forma eficiente. `http://www.thiengo.com.br/mvp-android`.
  
  * __Dagger2__: Um popular controlador de injeção de dependência, controla e otimiza as instâncias e seus tempos de vida de objetos cujo são necessários para ser utilizados no App, assim otimizando a memória do App. `https://github.com/ReactiveX/RxJava`.
  
  * __RxJava2__: Uma biblioteca popular que gerencia eventos assíncronos utilizando o padrão de projetos __Observer__, que monitora objetos observáveis e "reage" à emissão de dados dos mesmos. Essa "reação" entrega ao programador precisamente o momento em que esses dados esperados são recebidos, facilitando assim o fluxo assíncrono do App. `https://google.github.io/dagger/`.
  
  * __Testes com Espresso e MockWebServer:__: Espresso é uma biblioteca de testes instrumentados no Android, muito útil para testes que simulem uma interação humana. `https://developer.android.com/training/testing/espresso/index.html`, combinado com o MockWebServer da empresa da __square__, pode-se criar o ambiente ideal para qualquer tipo de teste de regra de negócio. `https://github.com/square/okhttp/tree/master/mockwebserver`.
  
  * __Testes Unitários com JUnit e Mockito__: Testes unitários da API e espaço para criar vários testes para os __Presenters__ do App. Junit: `http://junit.org/junit4/` Mockito: `http://site.mockito.org/`.

  * __ConstraintLayout__*: Uso intensivo do ConstraintLayout para aumentar a performance de UI do App.