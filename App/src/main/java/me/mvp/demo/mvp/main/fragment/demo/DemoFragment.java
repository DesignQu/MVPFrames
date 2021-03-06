package me.mvp.demo.mvp.main.fragment.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.jakewharton.rxbinding3.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import me.logg.Logg;
import me.mvp.demo.R;
import me.mvp.demo.db.AppDatabase;
import me.mvp.demo.entity.User;
import me.mvp.demo.entity.UserDao;
import me.mvp.demo.ui.widget.dialog.DialogDefault;
import me.mvp.frame.base.BaseFragment;
import me.mvp.frame.frame.IView;
import me.mvp.frame.frame.Message;
import me.mvp.frame.widget.imageloader.glide.GlideConfig;
import me.mvp.frame.widget.imageloader.glide.ImageScaleType;

/**
 * Demo
 */
public class DemoFragment extends BaseFragment<DemoPresenter> implements IView {

    @BindView(R.id.btn_dialog)
    AppCompatButton btnDialog;
    @BindView(R.id.img)
    AppCompatImageView img;

    public static Fragment create(int index) {
        DemoFragment fragment = new DemoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @SuppressLint("CheckResult")
    @Override
    public void create(Bundle savedInstanceState) {
        Logg.e("DemoFragment");

        User user = new User();
        user.setUserId("testid");
        user.setName("test");

        UserDao dao = AppDatabase.get(component).userDao();
        dao.insertAll(user);

        List<User> users = dao.getAll();
        Logg.e(users);

        // 演示使用框架提供的图片加载器加载图片
        component.getImageLoader().load(component.getApplication(), GlideConfig.builder()
                .url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1574138006631&di=0177e2d5ff26c99c0703f4c507cbeeca&imgtype=0&src=http%3A%2F%2Fwww.weyou360.com%2Fxueyuan%2Fuploads%2Fallimg%2F120517%2F1-12051GH63U09.jpg")
                .imageView(img)
                .build());

//        component.getImageLoader().clear(component.getApplication(), GlideConfig.builder()
//                .clearDiskCache(true)
//                .clearMemory(true)
//                .build());

        RxView.clicks(btnDialog)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(v -> DialogDefault.newInstance().show(getFragmentManager(), DialogDefault.TAG));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(int type, @NonNull String message) {

    }

    @Override
    public void handleMessage(@NonNull Message message) {

    }

    @Override
    public DemoPresenter obtainPresenter() {
        return new DemoPresenter(component);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_demo;
    }
}