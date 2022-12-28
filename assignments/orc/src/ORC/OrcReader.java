package ORC;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.ColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DoubleColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.TimestampColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.Reader;
import org.apache.orc.RecordReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class OrcReader {

    public static void reader(String filePath) throws IOException {
        Configuration conf = new Configuration();
        conf.setAllowNullValueProperties(true);
        Reader reader = OrcFile.createReader(new Path(filePath),
                OrcFile.readerOptions(conf));

        RecordReader rows = reader.rows();
        VectorizedRowBatch batch = reader.getSchema().createRowBatch();
        System.out.println("schema:" + reader.getSchema());
        System.out.println("numCols:" + batch.numCols);
        ColumnVector.Type[] colsMap = new ColumnVector.Type[batch.numCols];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        while (rows.nextBatch(batch)) {
            BytesColumnVector cols0 = (BytesColumnVector) batch.cols[0];
            LongColumnVector cols1 = (LongColumnVector) batch.cols[1];
            DoubleColumnVector cols2 = (DoubleColumnVector) batch.cols[2];
            TimestampColumnVector cols3 = (TimestampColumnVector) batch.cols[3];
            BytesColumnVector cols4 = (BytesColumnVector) batch.cols[4];


            for(int cols = 0; cols < batch.numCols; cols++) {
                System.out.println("args = [" + batch.cols[cols].type + "]");
            }

            for(int r=0; r < batch.size; r++) {
                String a = cols0.toString(r);
                System.out.println("date:" + cols1.vector[r]);
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(cols1.vector[r]));
                String b = LocalDate.ofEpochDay(cols1.vector[r]).atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                System.out.println("date:" + date);
                Double c = cols2.vector[r];
                Timestamp d = cols3.asScratchTimestamp(r);
                String e = cols4.toString(r);


                System.out.println(a + ", " + b + ", " + c + ", " + simpleDateFormat.format(d) + ", " + e);
            }
        }
        rows.close();
    }

}
