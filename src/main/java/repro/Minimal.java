package repro;

import com.google.cloud.NoCredentials;
import com.google.cloud.bigquery.*;

public class Minimal {

    public static void main(String[] args) throws InterruptedException {
        var client = BigQueryOptions
            .newBuilder()
            .setCredentials(NoCredentials.getInstance())
            .setProjectId("test-project")
            .setHost("http://fake-bq:9050")
            .build()
            .getService();


        var tableId = TableId.of("test", "test");

        var loadJobConfiguration =
            LoadJobConfiguration
                .builder(tableId, "gs://bucket/object.csv")
                .setFormatOptions(FormatOptions.csv())
                .build();

        var job = client.create(JobInfo.of(loadJobConfiguration));

        System.out.println("Waiting for job to complete...");
        job.waitFor();

    }
}
