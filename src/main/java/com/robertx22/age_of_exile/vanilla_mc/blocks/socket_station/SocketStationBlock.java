package com.robertx22.age_of_exile.vanilla_mc.blocks.socket_station;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.library_of_exile.tile_bases.BaseInventoryBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.BlockView;

public class SocketStationBlock extends BaseInventoryBlock {

    public SocketStationBlock() {
        super(Settings.of(Material.STONE)
            .strength(5F, 2));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new SocketStationBlockEntity();
    }

    @Override
    public Identifier getContainerId() {
        return ModRegistry.CONTAINERS.GEAR_SOCKET;
    }
}