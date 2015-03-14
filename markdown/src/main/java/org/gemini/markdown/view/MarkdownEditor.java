package org.gemini.markdown.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.gemini.markdown.R;

/**
 * Created by geminiwen on 15/3/14.
 */
public class MarkdownEditor extends RelativeLayout implements View.OnClickListener{

    private MarkdownEditText mEditText;

    private Button mTitleButton;
    private Button mListButton;
    private Button mCodeBlockButton;

    public MarkdownEditor(Context context) {
        this(context, null);
    }

    public MarkdownEditor(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //TODO 这里的typedArray和资源文件的理解依然很混乱
    public MarkdownEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.MarkdownEditor, defStyleAttr, 0);
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        int layoutId = a.getResourceId(R.styleable.MarkdownEditor_layout, R.layout.mk_editor_layout);
        inflater.inflate(layoutId, this, true);

        mEditText = (MarkdownEditText)findViewById(R.id.mkdroid_markdown_edit_text);

        mTitleButton = (Button)findViewById(R.id.btn_title);
        mListButton = (Button)findViewById(R.id.btn_list);
        mCodeBlockButton = (Button)findViewById(R.id.btn_code_block);

        mTitleButton.setOnClickListener(this);
        mListButton.setOnClickListener(this);
        mCodeBlockButton.setOnClickListener(this);

        a.recycle();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MarkdownEditor(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.MarkdownEditor, defStyleAttr, defStyleRes);
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        int layoutId = a.getResourceId(R.styleable.MarkdownEditor_layout, R.layout.mk_editor_layout);
        inflater.inflate(layoutId, this, true);

        a.recycle();
    }

    @Override
    public void onClick(View v) {
        Editable e =  mEditText.getText();
        if (v == mTitleButton) {
            insertHorizontalLine(e);
        } else if (v == mListButton) {
            insertList(e);
        } else if (v == mCodeBlockButton) {
            insertCodeBlock(e);
        }

    }

    private void insertTitle(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "# title";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 2, selectionStart + 7);
    }

    private void insertList(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String listTemplate = "- List Item";
        e.replace(selectionStart, selectionEnd, listTemplate);
        // start from "L", end to "m"
        mEditText.setSelection(selectionStart + 2, selectionStart + 11);
    }

    private void insertCodeBlock(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String codeBlockTemplate = "```\n\n```";
        e.replace(selectionStart, selectionEnd, codeBlockTemplate);
        mEditText.setSelection(selectionStart + 4, selectionStart + 4);
    }

    private void insertStrong(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "**Strong**";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 2, selectionStart + 8);
    }

    private void insertItalic(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "*Italic*";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 1, selectionStart + 7);
    }

    private void insertBlockquotes(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "> ";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 2, selectionStart + 2);
    }

    private void insertLink(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "[链接](http://example)";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 12, selectionStart + 19);
    }

    private void insertImage(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "![Image](http://resource)";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 16, selectionStart + 24);
    }

    private void insertHorizontalLine(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        int selectionEnd = mEditText.getSelectionEnd();
        final String titleTemplate = "---\n";
        e.replace(selectionStart, selectionEnd, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 4, selectionStart + 4);
    }
}
