package com.mozhuowen.rxandroidframework.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mozhuowen.rxandroid.activity.NormalActivity;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandler;
import com.mozhuowen.rxandroid.ui.handlers.ActionBarHandlerDefault;
import com.mozhuowen.rxandroidframework.R;
import com.mozhuowen.rxandroidframework.context.App;
import com.mozhuowen.rxandroidframework.model.entity.MovieItem;
import com.mozhuowen.rxandroidframework.presenter.SimpleEvePresenter;
import com.mozhuowen.rxandroidframework.ui.iView.TestEveView;
import com.mozhuowen.util.LogUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Awen on 16/6/8.
 * Email:mozhuowen@gmail.com
 */
public class EditEveActivity extends NormalActivity implements TestEveView {

    SimpleEvePresenter presenter;
    String dataId;
    MovieItem movieItem;


    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.url_detail)
    EditText url_detail;
    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.update)
    Button updatebutton;

    @Override
    public String getTitleString() {
        return "EditEveActivity";
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_testeve;
    }

    @Override
    protected void initPresenter() {
        presenter = new SimpleEvePresenter(this,this);
        presenter.init();
//        presenter.fetchOneData(dataId);
    }

    @Override
    public boolean isActionbarVisible() {
        return true;
    }

    @Override
    public boolean isDisplayHomeAsUpEnabled() {
        return true;
    }

    @Override
    protected boolean enableActionBarShadow() {
        return false;
    }

    @Override
    protected ActionBarHandler getActionBarHandler() {
        return new ActionBarHandlerDefault(this);
    }

    @Override
    protected boolean enableBackArrow() {
        return false;
    }

    @Override
    public void initViews() {
        dataId = getIntent().getExtras().getString("id");
        ButterKnife.bind(this);

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("button onClick!");
                String url_detail_new = url_detail.getText().toString();
                movieItem.setUrl_detail(url_detail_new);

                presenter.updateOneItem(movieItem);
            }
        });

        MovieItem item = App.getDbHash().get(dataId);
        if (item != null)
            onGetData(item);
        else
            presenter.getOneItem(dataId);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onGetData(MovieItem data) {
        this.movieItem = data;

        this.title.setText(movieItem.title);
        this.desc.setText(movieItem.desc);
        this.url_detail.setText(movieItem.url_detail);
        Glide.with(this).load(movieItem.image_urls.get(0)).crossFade().into(image);

//        initDb();
    }

    public void initDb() {
        App.getDbHash().put(movieItem._id,movieItem);

        MovieItem m = App.getDbHash().get(movieItem._id);
        Toast.makeText(EditEveActivity.this, m.url_detail, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showRefreshProgress() {

    }

    @Override
    public void hideRefreshProgress() {

    }

    /**Not used*/
    @Override
    public void showList(List<MovieItem> datalist) {

    }
    /**Not used*/
    @Override
    public void showList(List<MovieItem> datalist, boolean hasnext) {

    }
}
