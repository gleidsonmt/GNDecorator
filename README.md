

![Version](https://img.shields.io/badge/Version-2.1.21-green.svg?style=for-the-badge)
[![Release](https://img.shields.io/badge/Release-v2.1.21-alpha-blue.svg?style=for-the-badge)](https://github.com/Gleidson28/GNCarousel/releases/tag/1.0)
[![License](https://img.shields.io/github/license/Gleidson28/GNDecorator.svg?style=for-the-badge)](https://github.com/Gleidson28/GNCarousel/blob/master/LICENSE) 

<h1></h1>

<p align="center">
  <img src="src/logo.png"  />
</p>

<h1></h1>
<h6 align="center"> This project is part of the set of custom components created for JavaFx. </h6>

<h1></h1>

<h1>GNDecorator</h1>

<h5 > 
  GNDecorator is a simple decoration project for javaFx applications.
</h5>

 > Create a custom view for nodes.
 
```java
  GNDecorator window = new GNDecorator();
  window.setContent(root);
  window.initTheme(GNDecorator.Theme.DARKULA);
  window.setIcon(icon);
  window.setTitle("Title");
 ```
 
##### View Default
![demo1](src/com/gn/resources/screenshot/basic.png)
##### View Darkula
![demo1](src/com/gn/resources/screenshot/darkula.png)
##### With gradient and image
![demo1](src/com/gn/resources/screenshot/demo1.png)

### Adaptable when the bar is moved
![gif1](src/com/gn/resources/screenshot/gif1.gif)
### Full Screen animation
![gif2](src/com/gn/resources/screenshot/gif2.gif)

# Basic structure 

        root -> SstackPane
                body -> AnchorPane
                        top_left -> Path
                        top_right -> Path -- rotation 90°
                        bottom_left -> Path -- rotation 270°
                        bottom_right -> Path -- rotation 180°


## Structure - by Scenic View

![Structure](src/com/gn/resources/screenshot/primarySctructure.png)

