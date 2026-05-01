package fuzs.reachbehind.common.config;

public interface SharedConfig {
    boolean supportsCurrentEnvironment(boolean isClientSide);

    boolean flipPassClicksToAttachedBlock();
}
