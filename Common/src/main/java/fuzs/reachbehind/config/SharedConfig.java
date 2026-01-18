package fuzs.reachbehind.config;

public interface SharedConfig {
    boolean supportsCurrentEnvironment(boolean isClientSide);

    boolean flipPassClicksToAttachedBlock();
}
