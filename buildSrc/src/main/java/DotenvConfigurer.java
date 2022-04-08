import java.nio.file.Paths;
import java.util.Objects;

public final class DotenvConfigurer {

    private static final class LazyHolder {
        private static final DotenvConfigurer DEFAULT = new DotenvConfigurer(
            false,
            true,
            true,
            Paths.get(".").toAbsolutePath().normalize().toString(),
            ".env"
        );
    }

    private final boolean strictMode;
    private final boolean includeSystemVariables;
    private final boolean allowOverrideSystemVariables;
    private final String location;
    private final String filename;

    private DotenvConfigurer(
        final boolean strictMode,
        final boolean includeSystemVariables,
        final boolean allowOverrideSystemVariables,
        final String location,
        final String filename
    ) {
        Objects.requireNonNull(location, "location must not be null");
        Objects.requireNonNull(filename, "filename must not be null");

        this.strictMode = strictMode;
        this.includeSystemVariables = includeSystemVariables;
        this.allowOverrideSystemVariables = allowOverrideSystemVariables;
        this.location = location;
        this.filename = filename;
    }

    static DotenvConfigurer getInstance() {
        return LazyHolder.DEFAULT;
    }

    public Dotenv load() {
        try {
            final var systemEnvironment = System.getenv();
            final var fileContent = DotenvFileLoader.readDotenvFileAsProperties(location, filename);

            return new DotenvImpl(this, systemEnvironment, fileContent);
        } catch (final Exception e) {
            throw new LoadingException(e);
        }
    }

    public DotenvConfigurer strictMode(final boolean strictMode) {
        return new DotenvConfigurer(
            strictMode,
            includeSystemVariables,
            allowOverrideSystemVariables,
            location,
            filename
        );
    }

    public DotenvConfigurer includeSystemVariables(final boolean includeSystemVariables) {
        return new DotenvConfigurer(
            strictMode,
            includeSystemVariables,
            allowOverrideSystemVariables,
            location,
            filename
        );
    }


    public DotenvConfigurer allowOverrideSystemVariables(final boolean allowOverrideSystemVariables) {
        return new DotenvConfigurer(
            strictMode,
            includeSystemVariables,
            allowOverrideSystemVariables,
            location,
            filename
        );
    }


    public DotenvConfigurer location(final String location) {
        return new DotenvConfigurer(
            strictMode,
            includeSystemVariables,
            allowOverrideSystemVariables,
            location,
            filename
        );
    }

    public DotenvConfigurer filename(final String filename) {
        return new DotenvConfigurer(
            strictMode,
            includeSystemVariables,
            allowOverrideSystemVariables,
            location,
            filename
        );
    }

    public boolean isStrictMode() {
        return strictMode;
    }

    public boolean isIncludeSystemVariables() {
        return includeSystemVariables;
    }

    public boolean isAllowOverrideSystemVariables() {
        return allowOverrideSystemVariables;
    }

    public String getLocation() {
        return location;
    }

    public String getFilename() {
        return filename;
    }
}
