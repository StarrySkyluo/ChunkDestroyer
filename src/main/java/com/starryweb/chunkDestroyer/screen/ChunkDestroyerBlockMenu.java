package com.starryweb.chunkDestroyer.screen;

import com.starryweb.chunkDestroyer.block.entity.ChunkDestroyerBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ChunkDestroyerBlockMenu extends AbstractContainerMenu {
    public final ChunkDestroyerBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    // 构造函数
    public ChunkDestroyerBlockMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()),
                new SimpleContainerData(2));
    }

    public ChunkDestroyerBlockMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.CHUNK_DESTROYER_MENU.get(), pContainerId);
        //  删除 checkContainerSize()，因为没有物品槽
        blockEntity = (ChunkDestroyerBlockEntity) entity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv); // 保留玩家物品栏
        addPlayerHotbar(inv);    // 保留玩家快捷栏

        addDataSlots(data); // 同步数据
    }

    // 按钮按下处理
    public void onButtonPressed() {
        if (blockEntity != null) {
            blockEntity.onButtonPressed();
        }
    }

    // 检查是否能点击按钮
    public boolean canPressButton() {
        return data.get(1) <= 0; // 冷却时间为0
    }

    // 获取冷却时间
    public int getCooldown() {
        return data.get(1);
    }

    // 获取按钮状态
    public boolean isButtonPressed() {
        return data.get(0) == 1;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        // 因为没有自定义槽位，只处理玩家物品栏内部转移
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index < 36) {
                // 玩家物品栏内部转移
                if (!this.moveItemStackTo(itemstack1, 0, 36, true)) {
                    return ItemStack.EMPTY;
                }
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    // 处理服务端接收到的按钮点击
    @Override
    public boolean clickMenuButton(Player player, int buttonId) {
        if (buttonId == 0) { // 按钮ID为0
            if (canPressButton() && blockEntity != null) {
                blockEntity.onButtonPressed();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean stillValid(Player player) {
        // 添加距离检查，防止远程操作
        return player.distanceToSqr(
                (double)this.blockEntity.getBlockPos().getX() + 0.5D,
                (double)this.blockEntity.getBlockPos().getY() + 0.5D,
                (double)this.blockEntity.getBlockPos().getZ() + 0.5D
        ) <= 64.0D;
    }
}