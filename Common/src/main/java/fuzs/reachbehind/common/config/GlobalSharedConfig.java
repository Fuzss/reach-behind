package fuzs.reachbehind.common.config;

public interface GlobalSharedConfig extends SharedConfig {
    default GlobalSharedConfig plainCopy() {
        return new SharedState(this.requiresEmptyHand());
    }

    default GlobalSharedConfig setPassClicksThrough(boolean passClicksThrough) {
        return this;
    }
}
