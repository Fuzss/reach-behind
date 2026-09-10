package fuzs.reachbehind.common.config;

import fuzs.puzzleslib.common.api.config.v3.Config;
import fuzs.puzzleslib.common.api.config.v3.ConfigCore;
import fuzs.puzzleslib.common.api.config.v3.ValueCallback;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.Objects;

public class CommonConfig implements ConfigCore, GlobalSharedConfig {
    @Config(description = {
            "Support passing clicks through attached blocks only when interacting with an empty hand.",
            "This generally allows the attached block to still be used, like applying dyes and ink to signs."
    }, worldRestart = true)
    public boolean requiresEmptyHand = true;

    protected ModConfigSpec.ConfigValue<Boolean> passClicksThroughValue;
    private SharedConfig sharedConfig;

    public CommonConfig() {
        this.resetSharedConfig();
    }

    @Override
    public void addToBuilder(ModConfigSpec.Builder builder, ValueCallback callback) {
        this.passClicksThroughValue = builder.comment(
                        "Attempt interaction with the supporting block when clicking on an attached block before falling back to the originally clicked block itself.",
                        "Allows for opening attached containers such as chests and crafting tables without clicking on them directly.")
                .define("pass_clicks_to_attached_block", true);
    }

    public SharedConfig getSharedConfig(boolean isClientSide) {
        return isClientSide ? Objects.requireNonNull(this.sharedConfig) : this;
    }

    public void setSharedConfig(SharedConfig sharedConfig) {
        this.sharedConfig = sharedConfig;
    }

    public void resetSharedConfig() {
        this.sharedConfig = this;
    }

    public boolean togglePassClicksBehind() {
        this.passClicksThroughValue.set(!this.passClicksThroughValue.get());
        this.passClicksThroughValue.save();
        return this.passClicksThroughValue.get();
    }

    @Override
    public boolean passClicksThrough() {
        return this.passClicksThroughValue.get();
    }

    @Override
    public boolean requiresEmptyHand() {
        return this.requiresEmptyHand;
    }
}
