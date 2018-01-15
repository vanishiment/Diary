package com.plant.diary.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.plant.diary.R;

public class EditImageLayout extends FrameLayout implements View.OnClickListener {

  private ImageButton mIBtnAdd, mIBtnDelete;
  private ImageView mIv;
  private OnEditImageLayoutClickListener mL;

  public EditImageLayout(@NonNull Context context) {
    this(context, null);
  }

  public EditImageLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public EditImageLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    LayoutInflater.from(context).inflate(R.layout.widget_edit_image_layout, this);
    mIBtnAdd = findViewById(R.id.widget_edit_image_add_ib);
    mIBtnDelete = findViewById(R.id.widget_edit_image_delete_ib);
    mIv = findViewById(R.id.widget_edit_image_bg_iv);

    mIBtnDelete.setVisibility(GONE);

    mIBtnAdd.setOnClickListener(this);
    mIBtnDelete.setOnClickListener(this);
  }

  public void setOnEditImageLayoutClickListener(OnEditImageLayoutClickListener l) {
    this.mL = l;
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.widget_edit_image_add_ib:
        if (mL != null) mL.onAddClick(v);
        break;
      case R.id.widget_edit_image_delete_ib:
        if (mL != null) mL.onDeleteClick(v);
        break;
      default:
        break;
    }
  }

  public void setImageDrawable(Drawable drawable) {
    mIv.setImageDrawable(drawable);
    if (isShowDeleteButton()){
      mIBtnDelete.setVisibility(VISIBLE);
    } else {
      mIBtnDelete.setVisibility(GONE);
    }
  }

  public void setImageBitmap(Bitmap bitmap) {
    mIv.setImageBitmap(bitmap);
    if (isShowDeleteButton()){
      mIBtnDelete.setVisibility(VISIBLE);
    } else {
      mIBtnDelete.setVisibility(GONE);
    }
  }

  public void setImageResource(@DrawableRes int resId) {
    mIv.setImageResource(resId);
    if (isShowDeleteButton()){
      mIBtnDelete.setVisibility(VISIBLE);
    } else {
      mIBtnDelete.setVisibility(GONE);
    }
  }

  private boolean isShowDeleteButton(){
    return mIv.getDrawable() != null;
  }

  public interface OnEditImageLayoutClickListener {

    void onAddClick(View view);

    void onDeleteClick(View view);
  }
}
