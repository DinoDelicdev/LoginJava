// File: OvalHeaderView.java
package com.example.loginjava.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.example.loginjava.R;

public class OvalHeaderView extends ConstraintLayout {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path path = new Path();
    private String headerText = "Header";


    private ImageView backButton;
    private TextView headerTextView;

    public OvalHeaderView(Context context) {
        super(context);
        init(context, null);
    }

    public OvalHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public OvalHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ContextCompat.getColor(context, R.color.bingo_main));
        setWillNotDraw(false);
        LayoutInflater.from(context).inflate(R.layout.view_oval_header_content, this, true);
        backButton = findViewById(R.id.headerBackButton);
        headerTextView = findViewById(R.id.headerTitleText);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OvalHeaderView, 0, 0);
            headerText = typedArray.getString(R.styleable.OvalHeaderView_headerText);
            if (headerText == null) {
                headerText = "Header";
            }
            headerTextView.setText(headerText);
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float w = (float) getWidth();
        float h = (float) getHeight();

        path.reset();
        path.moveTo(0f, 0f);
        path.lineTo(w, 0f);
        path.lineTo(w, h * 0.75f);
        path.quadTo(w / 2, h * 1.1f, 0f, h * 0.75f);
        path.close();

        canvas.drawPath(path, paint);
    }

    public interface OnBackClickListener {
        void onBackClick();
    }

    public void setOnBackClickListener(OnBackClickListener listener) {
        backButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBackClick();
            }
        });
    }
}