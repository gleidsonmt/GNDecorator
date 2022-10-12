

![Version](https://img.shields.io/badge/Version-2.1.27-green.svg?style=for-the-badge)
[![Release](https://img.shields.io/badge/Release-v2.1.21--alpha-blue.svg?style=for-the-badge)](https://github.com/Gleidson28/GNCarousel/releases/tag/1.0)
[![License](https://img.shields.io/github/license/Gleidson28/GNDecorator.svg?style=for-the-badge)](https://github.com/Gleidson28/GNCarousel/blob/master/LICENSE) 

<h1></h1>

<p align="center">
  <img src="logo_flier.png"  />
</p>

<h1></h1>
<h6 align="center"> This project is part of the set of custom components created for JavaFx. </h6>

<h1></h1>

<h1>GNDecorator</h1>

<h5 > 
  GNDecorator is a simple decoration project for javaFx applications.
</h5>

 
##### View Default
![demo1](src/main/resources/screens/default.png)
##### View Darkula
![demo1](src/main/resources/screens/dark.png)
##### With gradient and image
![demo1](src/main/resources/screens/mac.png)

# Basic Structure 

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

 > Constrotors
 
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

## On Youtube
[Apresentation](https://youtu.be/hZsYU7UbWmU)