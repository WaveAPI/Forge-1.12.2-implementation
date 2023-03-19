package org.waveapi.api.misc;


import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class Text {

    public Text (ITextComponent text) {
        this.text = text;
    }
    public final ITextComponent text;
    public String getText() {
        return text.getFormattedText();
    }

    public static Text plain(String text) {
        return new Text(new TextComponentString(text));
    }
    public static Text translatable(String text) {
        return new Text(new TextComponentTranslation(text));
    }

}
