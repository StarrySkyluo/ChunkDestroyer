package com.starryweb.chunkDestroyer.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.starryweb.chunkDestroyer.ChunkDestroyer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ChunkDestroyerBlockScreen extends AbstractContainerScreen<ChunkDestroyerBlockMenu> {
   // GUI 纹理路径
   private static final ResourceLocation TEXTURE =
       new ResourceLocation(ChunkDestroyer.MOD_ID, "textures/gui/chunk_destroyer_block_gui.png");

// 按钮位置和大小（相对于GUI窗口）
private static final int BUTTON_X = 79;
private static final int BUTTON_Y = 58;
private static final int BUTTON_WIDTH = 18;  // 96 - 79 = 17，但通常用18
private static final int BUTTON_HEIGHT = 18; // 75 - 58 = 17，但通常用18

public ChunkDestroyerBlockScreen(ChunkDestroyerBlockMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
super(pMenu, pPlayerInventory, pTitle);
}

@Override
protected void init() {
super.init();
// 隐藏标题和物品栏标签（如果需要）
this.inventoryLabelY = 10000;
this.titleLabelY = 10000;
}

@Override
protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, TEXTURE);

// 计算GUI窗口在屏幕上的位置（居中）
int x = (width - imageWidth) / 2;
int y = (height - imageHeight) / 2;

// 渲染GUI背景
guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

// 渲染按钮状态（如果需要显示按下/未按下状态）
renderButton(guiGraphics, x, y);
}

// 渲染按钮状态
private void renderButton(GuiGraphics guiGraphics, int x, int y) {
// 如果按钮被按下，可以在这里渲染不同的纹理
// 例如：从纹理的某个位置绘制按钮的按下状态
if (menu.isButtonPressed()) {
   // 按钮按下状态 - 从纹理的 (79, 58) 位置绘制，或者使用其他纹理坐标
   // guiGraphics.blit(TEXTURE, x + BUTTON_X, y + BUTTON_Y, 
   //     79, 58, BUTTON_WIDTH, BUTTON_HEIGHT);
}

// 如果按钮在冷却中，可以渲染半透明效果
if (!menu.canPressButton()) {
   // 可以在这里添加冷却效果的渲染
   // 例如：绘制一个半透明的覆盖层
}
}

@Override
public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
renderBackground(guiGraphics);
super.render(guiGraphics, mouseX, mouseY, partialTick);
renderTooltip(guiGraphics, mouseX, mouseY);

// 如果鼠标悬停在按钮上，显示提示信息
if (isMouseOverButton(mouseX, mouseY)) {
   if (!menu.canPressButton()) {
       // 显示冷却时间提示
       guiGraphics.renderTooltip(this.font, 
           Component.literal("冷却中: " + menu.getCooldown() + " 刻"), 
           mouseX, mouseY);
   } else {
       // 显示按钮提示
       guiGraphics.renderTooltip(this.font, 
           Component.literal("点击激活"), 
           mouseX, mouseY);
   }
}
}

// 检查鼠标是否在按钮区域内
private boolean isMouseOverButton(int mouseX, int mouseY) {
int x = (width - imageWidth) / 2;
int y = (height - imageHeight) / 2;

int buttonLeft = x + BUTTON_X;
int buttonTop = y + BUTTON_Y;
int buttonRight = buttonLeft + BUTTON_WIDTH;
int buttonBottom = buttonTop + BUTTON_HEIGHT;

return mouseX >= buttonLeft && mouseX < buttonRight &&
      mouseY >= buttonTop && mouseY < buttonBottom;
}

@Override
public boolean mouseClicked(double mouseX, double mouseY, int button) {
// 只处理左键点击
if (button == 0 && isMouseOverButton((int) mouseX, (int) mouseY)) {
   // 检查是否可以点击（不在冷却中）
   if (menu.canPressButton()) {
       // 发送按钮点击事件到服务端
       // 使用 Menu 的 clickMenuButton 方法
       this.minecraft.gameMode.handleInventoryButtonClick(
           this.menu.containerId, 
           0  // 按钮ID，可以是任意数字，只要服务端能识别
       );
       return true; // 表示已处理点击事件
   }
   return true; // 即使不能点击，也阻止其他操作
}
return super.mouseClicked(mouseX, mouseY, button);
}
}
