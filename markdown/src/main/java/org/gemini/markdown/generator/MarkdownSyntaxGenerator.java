package org.gemini.markdown.generator;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;

import org.gemini.markdown.model.MarkdownSyntaxModel;
import org.gemini.markdown.model.type.MarkdownSyntaxType;
import org.gemini.markdown.model.type.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by geminiwen on 15/3/13.
 */
public class MarkdownSyntaxGenerator {

    private static Pattern getMatcherFromSyntaxType(MarkdownSyntaxType type) {
        switch(type) {
            case MarkdownSyntaxUnknown: {
                return null;
            }
            case MarkdownSyntaxHeaders: {
                return Pattern.compile("(#+)(.*)", Pattern.MULTILINE);
            }
            case MarkdownSyntaxLinks: {
                return Pattern.compile("\\[([^\\[]+)\\]\\(([^\\)]+)\\)", Pattern.CASE_INSENSITIVE);
            }
            case MarkdownSyntaxBold: {
                return Pattern.compile("(\\*\\*|__)(.*?)\\1", Pattern.CASE_INSENSITIVE);
            }
            case MarkdownSyntaxEmphasis: {
                return Pattern.compile("\\s(\\*|_)(.*?)\\1\\s", Pattern.CASE_INSENSITIVE);
            }
            case MarkdownSyntaxDeletions: {
                return Pattern.compile("\\~\\~(.*?)\\~\\~", Pattern.CASE_INSENSITIVE);
            }
            case MarkdownSyntaxQuotes: {
                return Pattern.compile("\\:\\\"(.*?)\\\"\\:", Pattern.CASE_INSENSITIVE);
            }
            case MarkdownSyntaxInlineCode: {
                return Pattern.compile("`(.*?)`", Pattern.CASE_INSENSITIVE);
            }
            case MarkdownSyntaxCodeBlock: {
                return Pattern.compile("```([\\s\\S]*?)```", Pattern.CASE_INSENSITIVE);
            }
            case MarkdownSyntaxBlockquotes: {
                return Pattern.compile("\n(&gt;|\\>)(.*)", Pattern.CASE_INSENSITIVE);
            }
            case MarkdownSyntaxULLists: {
                return Pattern.compile("^\\*([^\\*]*)", Pattern.MULTILINE);
            }
            case MarkdownSyntaxOLLists: {
                return Pattern.compile("^[0-9]+\\.(.*)", Pattern.MULTILINE);
            }
            case NumberOfMarkdownSyntax: {
                return null;
            }
            default: {
                return null;
            }
        }
    }

    public static CharacterStyle styleFromSyntaxType(MarkdownSyntaxType type) {
        switch(type) {
            case MarkdownSyntaxUnknown: {
                return null;
            }
            case MarkdownSyntaxHeaders: {
                //TODO adjust text size
                return new AbsoluteSizeSpan(50, true);
            }
            case MarkdownSyntaxLinks: {
                return new ForegroundColorSpan(Color.BLUE);
            }
            case MarkdownSyntaxBold: {
                return new StyleSpan(Typeface.BOLD);
            }
            case MarkdownSyntaxEmphasis: {
                return new StyleSpan(Typeface.BOLD);
            }
            case MarkdownSyntaxDeletions: {
                return new StrikethroughSpan();
            }
            case MarkdownSyntaxQuotes: {
                return new ForegroundColorSpan(Color.LTGRAY);
            }
            case MarkdownSyntaxInlineCode: {
                return new ForegroundColorSpan(Color.YELLOW);
            }
            case MarkdownSyntaxCodeBlock: {
                return new BackgroundColorSpan(Color.parseColor("#fafafa"));
            }
            case MarkdownSyntaxBlockquotes: {
                return new ForegroundColorSpan(Color.LTGRAY);
            }
            case MarkdownSyntaxULLists: {
                return null;
            }
            case MarkdownSyntaxOLLists: {
                return null;
            }
            case NumberOfMarkdownSyntax: {
                return null;
            }
            default: {
                return null;
            }
        }
    }

    public static List<MarkdownSyntaxModel> syntaxModelsForString(String text) {
        List<MarkdownSyntaxModel> models = new ArrayList<>();
        for (MarkdownSyntaxType type: MarkdownSyntaxType.values()) {
            Pattern pattern = getMatcherFromSyntaxType(type);
            if (pattern == null) continue;
            Matcher matcher = pattern.matcher(text);
            while(matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                MarkdownSyntaxModel model = new MarkdownSyntaxModel(type, new Range(start, end));
                models.add(model);
            }
        }
        return models;
    }
}
