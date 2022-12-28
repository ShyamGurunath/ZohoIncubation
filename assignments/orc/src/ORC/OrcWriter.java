package ORC;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DoubleColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.TimestampColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;



public class OrcWriter {


    public static String writeOrc() throws IOException, ParseException {
        Configuration conf = new Configuration();
        conf.set("orc.stripe.size","1000000");
        conf.set("orc.compression","SNAPPY");
        conf.setBoolean("orc.create.index",true);
        TypeDescription schema = TypeDescription.createStruct()
                .addField("a-string", TypeDescription.createString())
                .addField("b-date", TypeDescription.createDate())
                .addField("c-double", TypeDescription.createDouble())
                .addField("d-time", TypeDescription.createTimestamp())
                .addField("e-string", TypeDescription.createString());

        String orcFile = "/home/shyam-inc3588/orc-test-" + System.currentTimeMillis() + ".orc";

        if(Files.exists(Paths.get(orcFile))) {
            Files.delete(Paths.get(orcFile));
        }

        Writer writer = OrcFile.createWriter(new Path(orcFile),
                OrcFile.writerOptions(conf)
                        .setSchema(schema));

        VectorizedRowBatch batch = schema.createRowBatch();
        BytesColumnVector a = (BytesColumnVector) batch.cols[0];
        LongColumnVector b = (LongColumnVector) batch.cols[1];
        DoubleColumnVector c = (DoubleColumnVector) batch.cols[2];
        TimestampColumnVector d = (TimestampColumnVector) batch.cols[3];
        BytesColumnVector e = (BytesColumnVector) batch.cols[4];

        for(int r=0; r < 5000000; ++r) {
            int row = batch.size++;
                  a.setVal(row, ("a-" + r).getBytes());
      a.setVal(row, ("a-" + r).getBytes());
            b.vector[row] = LocalDate.parse("2019-07-22").minusDays(r).toEpochDay();
            c.vector[row] = Double.valueOf(r);
            d.set(row, new Timestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse("2019-07-22T01:12:37.758-0500").getTime()));
            e.setVal(row, ("e-" + r).getBytes());
            // If the batch is full, write it out and start over.
            if (batch.size == batch.getMaxSize()) {
                writer.addRowBatch(batch);
                batch.reset();
            }
        }
        if (batch.size != 0) {
            writer.addRowBatch(batch);
            batch.reset();
        }

            writer.close();

        return orcFile;
    }
}
