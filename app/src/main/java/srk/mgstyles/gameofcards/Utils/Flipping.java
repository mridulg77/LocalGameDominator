package srk.mgstyles.gameofcards.Utils;

import android.graphics.Matrix;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.graphics.Camera;
import android.view.animation.Transformation;

public class Flipping extends Animation {
    private Camera camera;

    private View fromView;

    private View toView;

    private float centerX;

    private float centerY;

    private boolean forward = true;

    private boolean visibilitySwapped;

    /**
     * Creates a 3D flip animation between two views. If forward is true, its
     * assumed that view1 is "visible" and view2 is "gone" before the animation
     * starts. At the end of the animation, view1 will be "gone" and view2 will
     * be "visible". If forward is false, the reverse is assumed.
     *
     * @param fromView First view in the transition.
     * @param toView   Second view in the transition.
     * @param centerX  The center of the views in the x-axis.
     * @param centerY  The center of the views in the y-axis.
     */
    public Flipping(View fromView, View toView, int centerX, int centerY) {
        this.fromView = fromView;
        this.toView = toView;
        this.centerX = centerX;
        this.centerY = centerY;

        setDuration(700);
        setFillAfter(true);
        setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void reverse() {
        forward = false;
        View temp = toView;
        toView = fromView;
        fromView = temp;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        // Angle around the y-axis of the rotation at the given time. It is
        // calculated both in radians and in the equivalent degrees.
        final double radians = Math.PI * interpolatedTime;
        float degrees = (float) (180.0 * radians / Math.PI);

        // Once we reach the midpoint in the animation, we need to hide the
        // source view and show the destination view. We also need to change
        // the angle by 180 degrees so that the destination does not come in
        // flipped around. This is the main problem with SDK sample, it does not
        // do this.
        if (interpolatedTime >= 0.5f) {
            degrees -= 180.f;

            if (!visibilitySwapped) {
                fromView.setVisibility(View.GONE);
                toView.setVisibility(View.VISIBLE);

                visibilitySwapped = true;
            }
        }

        if (forward)
            degrees = -degrees;

        final Matrix matrix = t.getMatrix();

        camera.save();
        camera.translate(0.0f, 0.0f, (float) (150.0 * Math.sin(radians)));
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
