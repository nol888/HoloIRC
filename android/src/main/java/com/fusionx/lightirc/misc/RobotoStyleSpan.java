package com.fusionx.lightirc.misc;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

class RobotoStyleSpan extends MetricAffectingSpan {
    private final Factory factory;
    private final int style;

    private RobotoStyleSpan(Factory factory, int style) {
        this.factory = factory;
        this.style = style;
    }

    @Override
    public void updateDrawState(final TextPaint drawState) {
        apply(drawState);
    }

    @Override
    public void updateMeasureState(final TextPaint paint) {
        apply(paint);
    }

    private void apply(final Paint paint) {
        final int oldStyle = typefaceToStyle(paint.getTypeface());
        final int newStyle = style | oldStyle;

        if (factory.styleTypefaceBiMap.containsKey(newStyle)) {
            paint.setTypeface(factory.styleTypefaceBiMap.get(newStyle));
        }
    }

    private int typefaceToStyle(Typeface typeface) {
        if (factory.styleTypefaceBiMap.containsValue(typeface)) {
            return factory.styleTypefaceBiMap.inverse().get(typeface);
        } else {
            return Typeface.NORMAL;
        }
    }

    static class Factory {
        private final BiMap<Integer, Typeface> styleTypefaceBiMap;

        Factory(final Context context) {
            final Typeface boldTypeface =
                    Typeface.createFromAsset(context.getAssets(), "Roboto-Medium.ttf");;
            final Typeface italicTypeface =
                    Typeface.createFromAsset(context.getAssets(), "Roboto-LightItalic.ttf");;
            final Typeface boldItalicTypeface =
                    Typeface.createFromAsset(context.getAssets(), "Roboto-MediumItalic.ttf");;

            styleTypefaceBiMap = ImmutableBiMap.of(
                    Typeface.BOLD, boldTypeface,
                    Typeface.ITALIC, italicTypeface,
                    Typeface.BOLD_ITALIC, boldItalicTypeface);
        }

        RobotoStyleSpan buildBold() {
            return new RobotoStyleSpan(this, Typeface.BOLD);
        }

        RobotoStyleSpan buildItalic() {
            return new RobotoStyleSpan(this, Typeface.ITALIC);
        }
    }
}