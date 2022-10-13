

<h1 align="center">GNDecorator</h1>
<h6 align="center"> This project is part of the set of custom components created for JavaFx. </h6>
<h1></h1>

[![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/io.github.gleidsonmt/gndecorator?server=https%3A%2F%2Fs01.oss.sonatype.org&style=for-the-badge)](https://central.sonatype.dev/artifact/io.github.gleidsonmt/gndecorator/2.1.25)
[![Release](https://img.shields.io/badge/Release-v2.1.25--beta-green.svg?style=for-the-badge)](https://github.com/gleidsonmt/GNDecorator/releases/tag/2.1.25)
[![License](https://img.shields.io/github/license/Gleidson28/GNDecorator.svg?style=for-the-badge)](https://github.com/gleidsonmt/GNDecorator/blob/master/LICENSE) 

<p align="center">
  <img src="./src/main/resources/logo/logo_flier.png"  />
</p>

# 📑 Contents

<!-- TOC -->
* [🚀 Installing Decorator](#-installing-decorator)
* [🧬  Basic Structure](#-basic-structure)
  * [Structure - by Scenic View](#structure---by-scenic-view)
    * [Default Structure](#default-structure)
        * [View Default](#view-default)
        * [View Darkula](#view-darkula)
        * [With gradient and image](#with-gradient-and-image)
  * [📺 On Youtube](#-on-youtube)
    * [🛠 Ajustes e melhorias](#-ajustes-e-melhorias)
  * [📫 Contribuindo para <nome_do_projeto>](#-contribuindo-para--nome_do_projeto-)
  * [😄 Seja um dos contribuidores<br>](#-seja-um-dos-contribuidores-br)
  * [📝 Licença](#-licena)
<!-- TOC -->

## 💻 Environment


This lib is a compnent for JavaFx:   Get in the offical website [JavaFx](https://openjfx.io/),</br>
Tutorial [Getting Started](https://openjfx.io/openjfx-docs/)

I have a great workstation here, then I've using a gradle 7.2 and Java and JavaFx +16 on Windows. (I really want to test in other systems in future).


# 🚀 Installing Decorator

Now you have many ways to get that!
!🗒️Note ** The release has the first changes I made and releases in nexus are more stable 🥸***

👌In code blocks find the copy button... is cooler, it automatically knows if your target is a pom.xml or gradle.build

For installing decorator, you have this options:

  * For Pros - Click on the badge release or click badge nexus on the top of this document, and you're going to redirect to hosted sources.
  * For Github Users - On the right side, you can see the packages and releases, click on them and download it, in case you are in packages just copy them into your build file.
  * For Faster users - Just copy and paste the code bellow

If you use maven add in pom.xml:
```xml
<dependency>
  <groupId>io.github.gleidsonmt</groupId>
  <artifactId>gndecorator</artifactId>
  <version>2.1.25</version>
</dependency>
```

.. or in gradle.build:
```groovy
    implementation 'io.github.gleidsonmt:gndecorator:2.1.25'
```

# ☕ Using Decorator

Begin to use... Java Code!

```java
  GNDecorator decorator = new GNDecorator();
 ```

> Setting content
```java
    decorator.setContent(content);
    decorator.fullBody() // the content occupies all of size
  ```

> Menus
```java
    Menu menu = new Menu("File");
    menu.getItems().add(new MenuItem("Open"));
    menu.getItems().add(new MenuItem("Close"));
    decorator.addMenu(menu);
    decorator.addMenu(1, menu);// add with a index
  ```

> Tittle
```java
    decorator.setTitle("JavaFx Application");
  ```

> Controls
```java
    ButtonTest a1 = new ButtonTest("Button 1");
    decorator.addControl(a1);
    decorator.addControl(index, a1); // add with a index
  ```


# 🧬  Basic Structure 

        root -> SstackPane
                body -> AnchorPane
                        top_left -> Path
                        top_right -> Path -- rotation 90°
                        bottom_left -> Path -- rotation 270°
                        bottom_right -> Path -- rotation 180°


## Structure - by Scenic View
![Structure](src/main/resources/screens/primarySctructure.png)

### Default Structure
![gif2](src/main/resources/screens/explanation.jpg)


##### View Default
![demo1](src/main/resources/screens/default.png)
##### View Darkula
![demo1](src/main/resources/screens/dark.png)
##### With gradient and image
![demo1](src/main/resources/screens/mac.png)

## 📺 On Youtube
[Apresentation](https://youtu.be/hZsYU7UbWmU)

### 🛠 Ajustes e melhorias

Next steps:

- [x] Add Yosemite Theme
- [x] Public methods to update window icons.
- [ ] Code outside the scope


Adicione comandos de execução e exemplos que você acha que os usuários acharão úteis. Fornece uma referência de opções para pontos de bônus!

## 📫 Contribuindo para <nome_do_projeto>
<!---Se o seu README for longo ou se você tiver algum processo ou etapas específicas que deseja que os contribuidores sigam, considere a criação de um arquivo CONTRIBUTING.md separado--->
Para contribuir com <nome_do_projeto>, siga estas etapas:

1. Bifurque este repositório.
2. Crie um branch: `git checkout -b <nome_branch>`.
3. Faça suas alterações e confirme-as: `git commit -m '<mensagem_commit>'`
4. Envie para o branch original: `git push origin <nome_do_projeto> / <local>`
5. Crie a solicitação de pull.

Como alternativa, consulte a documentação do GitHub em [como criar uma solicitação pull](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request).

## 😄 Seja um dos contribuidores<br>

Quer fazer parte desse projeto? Clique [AQUI](CONTRIBUTING.md) e leia como contribuir.

## 📝 Licença

Esse projeto está sob licença. Veja o arquivo [LICENÇA](LICENSE.md) para mais detalhes.

[⬆ Voltar ao topo](#nome-do-projeto)<br>
