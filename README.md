# JUIX  
Windows UI framework for everyone(prerelease state),  
uses the new Panama API coming, therefore **requires JDK 21 or JDK 20 with enabled preview** to run

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
