package pl.treekt.graphsforblindness.exception;

public class ResourceNotInitializedException extends RuntimeException {

    public ResourceNotInitializedException(String resourceName) {
        super("The resource " +  resourceName + " has not been initialized!");
    }
}
