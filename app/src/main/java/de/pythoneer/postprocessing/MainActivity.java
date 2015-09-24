package de.pythoneer.postprocessing;

import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.self.viewtoglrendering.R;

import java.util.Arrays;
import java.util.List;

import de.pythoneer.postprocessing.effects.CrtOneEffect;
import de.pythoneer.postprocessing.effects.DigitalGlitch;
import de.pythoneer.postprocessing.effects.InvertEffect;
import de.pythoneer.postprocessing.effects.PixelateEffect;
import de.pythoneer.postprocessing.effects.RgbShiftEffect;
import de.pythoneer.postprocessing.effects.SimpleScanLineEffect;
import de.pythoneer.postprocessing.effects.VhsEffect;
import de.pythoneer.postprocessing.effects.WobbleEffect;


public class MainActivity extends ActionBarActivity {

    private GLSurfaceView mGLSurfaceView;
    private GLRenderable mGLLinearLayout;
    private WebView mWebView;

    private int currentEffect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        final List<EffectItem> effects = Arrays.asList(
                new WobbleEffect(),             // 0
                new PixelateEffect(),           // 1
                new InvertEffect(),             // 2
                new SimpleScanLineEffect()  ,   // 3
                new CrtOneEffect(),             // 4
                new RgbShiftEffect(),           // 5
                new DigitalGlitch(),            // 6
                new VhsEffect()                 // 7
        );

        final BaseGLRenderer baseGlRenderer = new FooRenderer(this, width, height, effects);

        mGLSurfaceView = (GLSurfaceView) findViewById(R.id.gl_surface_view);
        mGLLinearLayout = (GLRenderable) findViewById(R.id.gl_layout);
        mWebView = (WebView) findViewById(R.id.web_view);

        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(baseGlRenderer);

        mGLLinearLayout.setViewToGLRenderer(baseGlRenderer);

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl("http://www.golem.de");


        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
//                ((FooRenderer) baseGlRenderer).otherProgram = !((FooRenderer) baseGlRenderer).otherProgram;


                final int setEffect = (currentEffect++) % effects.size();
                ((FooRenderer) baseGlRenderer).currentEffect = setEffect;

                h.postDelayed(this, 5000);
            }
        }, 5000);

    }


}
