package net.quedoom.multiblock_miner.enchantment.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.quedoom.multiblock_miner.config.QMMConfigManager;
import net.quedoom.multiblock_miner.enchantment.QSMultiblockMinerEnchantment;
import net.quedoom.multiblock_miner.things.MultiblockMineChecks;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Debug(export = true)
@Mixin(MultiblockMineChecks.class)
public class MakeMultimineReqEnchantmentMixin {
    private static final ResourceKey<Enchantment> MULTIMINE =
            ResourceKey.create(Registries.ENCHANTMENT, QSMultiblockMinerEnchantment.id("multimine"));

    @ModifyReturnValue(method = "enchantment", at = @At("RETURN"))
    private static boolean requireEnchantment(boolean original, Level level, ItemStack destroyedWith) {
        if (QMMConfigManager.CONFIG.requireEnchantment) {
            return destroyedWith.getEnchantments().keySet().stream()
                    .anyMatch(holder -> holder.is(MULTIMINE));
        }
        return original;
    }
}
