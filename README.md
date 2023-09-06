# JUIX  
Simple and easy to use Java Windows framework, written using the new project Panama coming with JDK 21

### Installation  
We currently don't provide any releases, you will have to build one yourself  

### Usage  
To make a simple window appear you will need 1 class and 1 property file:  
```java
// lol.koblizek.testapp.Main
public class Main extends Application<Window>
{
    @Override
    public void onInitialize(Window disposable) {
        // You can add window controls here
    }
}
```  
```properties
# resource file called juix.properties
entrypoint=lol.koblizek.testapp.Main
```  
To run it, create a new launch configuration and set BootstrapLauncher#main as a main method!  
That's all you need to do!
