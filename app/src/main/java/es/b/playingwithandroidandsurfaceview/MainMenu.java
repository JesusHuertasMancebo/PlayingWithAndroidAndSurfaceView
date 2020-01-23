package es.b.playingwithandroidandsurfaceview;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.ballActivity) {
            Intent intent = new Intent(this,MyBallActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.bouncingBallActivity) {
            Intent intent = new Intent(this,MyBouncingBallActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId(); Intent intent=null;
        if (id == R.id.home) {// Stopping other threads
            if (MyBouncingBallActivity.myBouncingBallSurfaceView !=null)
                MyBouncingBallActivity.myBouncingBallSurfaceView.stopThread();
            intent = new Intent (this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        if (id == R.id.ballActivity) { // Stopping other threads
            if (MyBouncingBallActivity.myBouncingBallSurfaceView !=null)
                MyBouncingBallActivity.myBouncingBallSurfaceView.stopThread();
            intent = new Intent (this, MyBallActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        if (id == R.id.bouncingBallActivity) {
            intent = new Intent (this, MyBouncingBallActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        startActivity(intent); //Starting the new activity
        return true;
    }
}

