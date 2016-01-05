package yv.recipe.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.yv.recipe.backend.process.Process;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import yv.recipe.Properties.Properties;
import yv.recipe.Properties.PropertyMapper;

/**
 * Created by yaron on 01/07/15.
 */
public class ProcessImageAsyncTask extends AsyncTask<Void, Void, String> {

    private Process processService = null;

    private Context context;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = Properties.instance().getPropertyString(PropertyMapper.GOOGLE_SENDER_ID);

    public ProcessImageAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        String msg = "";
        try {
            Process.Builder builder = new Process.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                    // otherwise they can be skipped
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            processService = builder.build();

            return processService.greet().execute().toString();

        } catch (Exception ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Logger.getLogger("PROCESS").log(Level.INFO, msg);
    }
}
