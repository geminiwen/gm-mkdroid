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
            insertTitle(e);
        } else if (v == mListButton) {
            insertList(e);
        } else if (v == mCodeBlockButton) {
            insertCodeBlock(e);
        }

    }

    private void insertTitle(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        final String titleTemplate = "# title";
        e.insert(selectionStart, titleTemplate);
        // start from "t", end to "e"
        mEditText.setSelection(selectionStart + 2, selectionStart + 7);
    }

    private void insertList(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        final String listTemplate = "- List Item";
        e.insert(selectionStart, listTemplate);
        // start from "L", end to "m"
        mEditText.setSelection(selectionStart + 2, selectionStart + 11);
    }

    private void insertCodeBlock(Editable e) {
        int selectionStart = mEditText.getSelectionStart();
        final String codeBlockTemplate = "```\n\n```";
        e.insert(selectionStart, codeBlockTemplate);
        mEditText.setSelection(selectionStart + 4, selectionStart + 4);
    }
}
