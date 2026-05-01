package fuzs.reachbehind.common.config;

import fuzs.puzzleslib.common.api.config.v3.Config;
import fuzs.puzzleslib.common.api.config.v3.ConfigCore;
import fuzs.puzzleslib.common.api.config.v3.ValueCallback;
import fuzs.puzzleslib.common.api.network.v4.NetworkingHelper;
import fuzs.reachbehind.common.ReachBehind;
import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig implements ConfigCore, SharedConfig {
    @Config(description = {
            "Support passing clicks through attached blocks only when interacting with an empty hand.",
            "This generally allows the attached block to still be used, like applying dyes and ink to signs."
    })
    public boolean requiresEmptyHand = true;
    protected ModConfigSpec.ConfigValue<Boolean> passClicksToAttachedBlockValue;

    @Override
    public void addToBuilder(ModConfigSpec.Builder builder, ValueCallback callback) {
        this.passClicksToAttachedBlockValue = builder.comment(
                "Attempt interaction with the supporting block when clicking on an attached block before falling back to the originally clicked block itself.",
                "Allows for opening attached containers such as chests and crafting tables without clicking on them directly.",
                this.getEffectiveEnvironmentLine()).define("pass_clicks_to_attached_block", true);
    }

    @Override
    public boolean supportsCurrentEnvironment(boolean isClientSide) {
        if (!this.passClicksToAttachedBlockValue.get()) {
            return false;
        } else if (isClientSide && !NetworkingHelper.isModPresentServerside(ReachBehind.MOD_ID)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean flipPassClicksToAttachedBlock() {
        this.passClicksToAttachedBlockValue.set(!this.passClicksToAttachedBlockValue.get());
        this.passClicksToAttachedBlockValue.save();
        return this.passClicksToAttachedBlockValue.get();
    }

    String getEffectiveEnvironmentLine() {
        return "This option only takes effect in singleplayer and for players on a multiplayer server which has this mod installed.";
    }
}
