public class Logger {
    private static Logger ourInstance = new Logger();

    public static Logger getInstance() {
        return ourInstance;
    }

    private Logger() { }

    public void write(String text) {
        //TODO: implement this
        System.out.println("Logger.Write Not implemented");
    }
}
