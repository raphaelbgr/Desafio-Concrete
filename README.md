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
  
  
  
  
  # README.MD ORIGINAL (CONCRETE)
  
  # Criar um aplicativo de consulta a API do [GitHub](https://github.com)#

Criar um aplicativo para consultar a [API do GitHub](https://developer.github.com/v3/) e trazer os repositÃ³rios mais populares de Java. Basear-se no mockup fornecido:

![Captura de tela de 2015-10-22 11-28-03.png](https://bitbucket.org/repo/7ndaaA/images/3102804929-Captura%20de%20tela%20de%202015-10-22%2011-28-03.png)

### **Deve conter** ###

- __Lista de repositÃ³rios__. Exemplo de chamada na API: `https://api.github.com/search/repositories?q=language:Java&sort=stars&page=1`
  * PaginaÃ§Ã£o na tela de lista, com endless scroll / scroll infinito (incrementando o parÃ¢metro `page`).
  * Cada repositÃ³rio deve exibir Nome do repositÃ³rio, DescriÃ§Ã£o do RepositÃ³rio, Nome / Foto do autor, NÃºmero de Stars, NÃºmero de Forks
  * Ao tocar em um item, deve levar a lista de Pull Requests do repositÃ³rio
- __Pull Requests de um repositÃ³rio__. Exemplo de chamada na API: `https://api.github.com/repos/<criador>/<repositÃ³rio>/pulls`
  * Cada item da lista deve exibir Nome / Foto do autor do PR, TÃ­tulo do PR, Data do PR e Body do PR
  * Ao tocar em um item, deve abrir no browser a pÃ¡gina do Pull Request em questÃ£o

### **A soluÃ§Ã£o DEVE conter** ##
* Sistema de build Gradle
* Mapeamento JSON -> Objeto (GSON / Jackson / Moshi / etc)
* Material Design

### **Ganha + pontos se conter** ###

* Framework para comunicaÃ§Ã£o com API
* Testes no projeto (unitÃ¡rios e por tela)
* Testes funcionais (que naveguem pelo aplicativo como casos de uso)
* Cache de imagens e da API
* Suportar mudanÃ§as de orientaÃ§Ã£o das telas sem perder estado

### **SugestÃµes** ###

As sugestÃµes de bibliotecas fornecidas sÃ£o sÃ³ um guideline, sintam-se a vontade para usar diferentes e nos surpreenderem. O importante de fato Ã© que os objetivos macros sejam atingidos. =)

* AndroidAnnotations
* Retrofit | Volley | Spring-Android
* Picasso | Universal Image Loader | Glide
* Espresso | Robotium | Robolectric

### **OBS** ###

A foto do mockup Ã© meramente ilustrativa.  


### **Processo de submissÃ£o** ###

O candidato deverÃ¡ implementar a soluÃ§Ã£o e enviar um pull request para este repositÃ³rio com a soluÃ§Ã£o.

O processo de Pull Request funciona da seguinte maneira:

1. Candidato farÃ¡ um fork desse repositÃ³rio (nÃ£o irÃ¡ clonar direto!)
2. FarÃ¡ seu projeto nesse fork.
3. CommitarÃ¡ e subirÃ¡ as alteraÃ§Ãµes para o __SEU__ fork.
4. Pela interface do Bitbucket, irÃ¡ enviar um Pull Request.

Se possÃ­vel deixar o fork pÃºblico para facilitar a inspeÃ§Ã£o do cÃ³digo.

### **ATENÃ‡ÃƒO** ###

NÃ£o se deve tentar fazer o PUSH diretamente para ESTE repositÃ³rio!
