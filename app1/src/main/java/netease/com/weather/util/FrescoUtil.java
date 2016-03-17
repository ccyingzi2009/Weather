package netease.com.weather.util;

import android.graphics.drawable.Animatable;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import javax.annotation.Nullable;

/**
 * Created by user on 16-3-17.
 */
public class FrescoUtil {

    public static void loadImage(final SimpleDraweeView draweeView, Uri uri){
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(uri);
        controller.setOldController(draweeView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>(){
            @Override
            public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (draweeView != null && imageInfo != null) {
                    draweeView.setAspectRatio(imageInfo.getWidth() / imageInfo.getHeight());
                }
            }
        });
        draweeView.setController(controller.build());
    }
}
