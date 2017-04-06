package com.huhx0015.poa.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huhx0015.poa.R;
import com.huhx0015.poa.models.Article;
import com.huhx0015.poa.network.ImageLoader;
import com.huhx0015.poa.utils.IntentUtils;

/**
 * Created by Michael Yoon Huh on 4/5/2017.
 */

public class ArticleView extends RelativeLayout {

    /** CLASS VARIABLE _________________________________________________________________________ **/

    private ImageView mArticleImage;
    private LinearLayout mArticleLayout;
    private TextView mArticleTitle;
    private TextView mArticleDescription;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public ArticleView(Context context) {
        super(context);
        initView(context);
    }

    public ArticleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ArticleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ArticleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    /** INITIALIZATION METHODS _________________________________________________________________ **/

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View articleView = inflater.inflate(R.layout.view_article_children, this, true);

        mArticleImage = (ImageView) articleView.findViewById(R.id.view_article_image);
        mArticleLayout = (LinearLayout) articleView.findViewById(R.id.article_view_layout);
        mArticleTitle = (TextView) articleView.findViewById(R.id.view_article_title);
        mArticleDescription = (TextView) articleView.findViewById(R.id.view_article_description);
    }

    public static ArticleView inflate(ViewGroup parent) {
        ArticleView articleView = (ArticleView) LayoutInflater.from(parent.getContext()).inflate(R.layout.view_article, parent, false);
        return articleView;
    }

    public void setArticle(final Article article, final Activity activity) {
        if (article != null) {

            if (article.getUrlToImage() != null) {
                mArticleImage.setVisibility(View.VISIBLE);
                mArticleImage.setImageBitmap(null);
                ImageLoader.loadImage(mArticleImage, article.getUrlToImage(), activity);
            } else {
                mArticleImage.setVisibility(View.GONE);
            }

            if (article.getTitle() != null) {
                mArticleTitle.setText(article.getTitle());
            }

            if (article.getDescription() != null) {
                mArticleDescription.setText(article.getDescription());
            }

            if (article.getUrl() != null) {
                mArticleLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentUtils.launchBrowerIntent(article.getUrl(), activity);
                    }
                });
            }
        }
    }
}