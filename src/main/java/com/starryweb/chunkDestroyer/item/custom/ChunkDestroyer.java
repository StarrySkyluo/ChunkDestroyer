package com.starryweb.chunkDestroyer.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class ChunkDestroyer extends Item {

    public ChunkDestroyer(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos blockPos = pContext.getClickedPos();
        Player player = pContext.getPlayer();
        Level world = pContext.getLevel();

        if(!world.isClientSide) {
            //获取区块坐标
            int chunkX = blockPos.getX()>>4;
            int chunkZ = blockPos.getZ()>>4;

            //获得区块对象的坐标范围
            int chunkXs0 = chunkX*16;
            int chunkXs1 = chunkX*16+15;
            int chunkZs0 = chunkZ*16;
            int chunkZs1 = chunkZ*16+15;

            for (int i=blockPos.getY();i>=-64;i--) {
                for (int j=chunkXs0;j<=chunkXs1;j++) {
                    for (int k=chunkZs0;k<=chunkZs1;k++) {
                        BlockPos pos1 = new BlockPos(j,i,k);
                        BlockState state = world.getBlockState(pos1);
                        FluidState fluidState = world.getFluidState(pos1);
                        //检测是否为空气（是则跳过）
                        if (state.isAir()) {
                            continue;
                        }else if(!state.getFluidState().isEmpty()){
                            //检测是否为流体
                            //是否为源头
                            if(fluidState.isSource()){
                                //设置为空气
                                world.setBlock(pos1, Blocks.AIR.defaultBlockState(),Block.UPDATE_ALL);
                                //触发流体更新
                                world.scheduleTick(pos1,fluidState.getType(),1);
                            }
                            world.setBlock(pos1, Blocks.AIR.defaultBlockState(),Block.UPDATE_ALL);

                        }else{
                            //删除区块内的方块
                            boolean destroyed = world.destroyBlock(pos1, false);
                        }

                    }
                }
            }

            player.sendSystemMessage(Component.literal("区块删除完成！"));


            //掉耐久
            pContext.getItemInHand().hurtAndBreak(1,player,
                    (p)->{
                p.broadcastBreakEvent(pContext.getHand());
            });
            return InteractionResult.SUCCESS;
        }

        return super.useOn(pContext);
    }
}
