package com.kaige.frescoimage;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 资料地址:https://www.fresco-cn.org/docs/requesting-multiple-images.html
 */
public class MainActivity extends AppCompatActivity {
    @ViewInject(R.id.image_view)
    private SimpleDraweeView draweeView;

    private  String img_url="http://gb.cri.cn/mmsource/images/2014/08/22/38/10202051462353342906.jpg";
    private  String img_url1="http://heilongjiang.sinaimg.cn/2015/0326/U10061P1274DT20150326104659.jpg";
    private  String img_url2="http://img4q.duitang.com/uploads/item/201411/20/20141120132318_3eAuc.thumb.700_0.jpeg";
    private  String img_url3="http://hiphotos.baidu.com/%CC%EC%C9%BD%B6%FE%CF%C0%B5%C4%D0%A1%CE%DD/pic/item/70c553e736d12f2e5b0614d64fc2d5628535682a.jpg";
    private  String img_url4="http://img4.douban.com/view/photo/raw/public/p1773847919.jpg";

    private  String img_url5="http://7mno4h.com2.z0.glb.qiniucdn.com/3859deb07d4647cf9183f8ea3f5aa165.jpg";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);     //初始化
//        Uri uri=Uri.parse(img_url);
//        draweeView.setImageURI(uri);
//        requestImage();
        moreImages();
    }
    private void requestImage(){
        ImageRequest request= ImageRequestBuilder.newBuilderWithSource(Uri.parse(img_url))
                .setProgressiveRenderingEnabled(true)
                .build();
        PipelineDraweeController controller= (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request).build();
        draweeView.setController(controller);
    }
    private void moreImages(){
        ControllerListener<ImageInfo> listener = new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFinalImageSet(
                    String id,
                    ImageInfo imageInfo,
                    Animatable anim) {

                if (imageInfo == null) {
                    return;
                }
                QualityInfo qualityInfo = imageInfo.getQualityInfo();
                FLog.d("Final image received! " +
                                "Size %d x %d",
                        "Quality level %d, good enough: %s, full quality: %s",
                        imageInfo.getWidth(),
                        imageInfo.getHeight(),
                        qualityInfo.getQuality(),
                        qualityInfo.isOfGoodEnoughQuality(),
                        qualityInfo.isOfFullQuality());
            }

            @Override
            public void onIntermediateImageSet(String id,  ImageInfo imageInfo) {
                FLog.d("","Intermediate image received");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                FLog.e(getClass(), throwable, "Error loading %s", id);
            }


        };
        DraweeController controller=Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(img_url5))
                .setImageRequest(ImageRequest.fromUri(img_url5+"/test"))
                .setControllerListener(listener)            //事件监听
                .build();
        draweeView.setController(controller);
    }
}
