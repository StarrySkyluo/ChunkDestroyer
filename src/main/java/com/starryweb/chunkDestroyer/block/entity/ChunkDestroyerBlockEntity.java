package com.starryweb.chunkDestroyer.block.entity;

import com.starryweb.chunkDestroyer.screen.ChunkDestroyerBlockMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class ChunkDestroyerBlockEntity extends BlockEntity implements MenuProvider {
    //0代表GUI里有0个物品栏
    private final ItemStackHandler itemHandler = new ItemStackHandler(0);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    //用于同步数据
    protected final ContainerData data;

    // 按钮相关状态
    private boolean buttonPressed = false;
    private int cooldown = 0; // 冷却时间
    private static final int MAX_COOLDOWN = 20; // 1秒冷却

    public ChunkDestroyerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CHUNK_DESTROYER_BLOCK_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex){
                    case 0 -> buttonPressed ? 1 : 0;
                    case 1 -> cooldown;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex){
                    case 0 -> buttonPressed = pValue == 1;
                    case 1 -> cooldown = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.chunk_destroyer_mod.chunk_destroyer_block");
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ChunkDestroyerBlockMenu(pContainerId,pPlayerInventory,this,this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("button_pressed", buttonPressed);
        pTag.putInt("cooldown", cooldown);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        buttonPressed = pTag.getBoolean("button_pressed");
        cooldown = pTag.getInt("cooldown");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        // 冷却时间处理
        if (cooldown > 0) {
            cooldown--;
            setChanged(); // 标记数据改变
        }
    }

    // 从GUI调用的方法
    public void onButtonPressed() {
        if (cooldown <= 0 && this.level != null && !this.level.isClientSide) {
            this.buttonPressed = true;
            this.cooldown = MAX_COOLDOWN;
            setChanged(); // 保存状态

            // 执行按钮按下后的逻辑
            executeButtonAction();

            // 通知客户端更新
            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    // 实际的按钮逻辑
    private void executeButtonAction() {
        if (this.level != null) {
            // 这里写你的按钮触发逻辑
            System.out.println("按钮被点击！位置：" + this.worldPosition);

            // 示例1：发送消息给附近的玩家

            Player player = this.level.getNearestPlayer(this.worldPosition.getX(),
                                                       this.worldPosition.getY(),
                                                       this.worldPosition.getZ(),
                                                       10, false);
            if (player != null) {
                player.sendSystemMessage(Component.literal("区块破坏器已激活！"));
            }


            // 破坏周围的方块
            //获取区块坐标
            BlockPos blockPos = this.worldPosition;

            int chunkX = blockPos.getX()>>4;
            int chunkZ = blockPos.getZ()>>4;

            //获得区块对象的坐标范围
            int chunkXs0 = chunkX*16-16;
            int chunkXs1 = chunkX*16+15+16;
            int chunkZs0 = chunkZ*16-16;
            int chunkZs1 = chunkZ*16+15+16;


            for (int i = blockPos.getY(); i >=-64; i--) {
                for (int j = chunkXs0; j <= chunkXs1; j++) {
                    for (int k = chunkZs0; k <= chunkZs1; k++) {
                        BlockPos pos1 = new BlockPos(j, i, k);
                        BlockState state = this.level.getBlockState(pos1);
                        FluidState fluidState = this.level.getFluidState(pos1);

                        //检测是否为空气
                        if(state.isAir()){

                        }else if(!fluidState.isEmpty()){
                            //检验是否为流体
                            //检验是否为源头
                            if(fluidState.isSource()){
                                //设置为空气
                                this.level.setBlock(pos1, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                                //触发流体更新
                                this.level.scheduleTick(pos1,fluidState.getType(),1);
                            }
                            this.level.setBlock(pos1, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                        }else if(pos1.equals(this.worldPosition)){
                            // 不破坏自身
                        }
                        else{
                            //删除区块内方块
                            this.level.destroyBlock(pos1, false);
                        }
                    }
                }
            }
        }
    }

    // 获取按钮状态（用于GUI渲染）
    public boolean isButtonPressed() {
        return buttonPressed;
    }

    // 获取冷却时间（用于GUI显示）
    public int getCooldown() {
        return cooldown;
    }

    // 重置按钮状态
    public void resetButton() {
        this.buttonPressed = false;
        setChanged();
    }

}
