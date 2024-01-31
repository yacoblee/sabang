import com.supermap.analyst.spatialanalyst.GridStatisticsMode;
import com.supermap.analyst.spatialanalyst.StatisticsAnalyst;
import com.supermap.analyst.spatialanalyst.ZonalStatisticsAnalystParameter;
import com.supermap.analyst.spatialanalyst.ZonalStatisticsAnalystResult;
import com.supermap.data.CursorType;
import com.supermap.data.Dataset;
import com.supermap.data.DatasetGrid;
import com.supermap.data.DatasetVector;
import com.supermap.data.Datasets;
import com.supermap.data.Datasource;
import com.supermap.data.DatasourceConnectionInfo;
import com.supermap.data.Datasources;
import com.supermap.data.EngineType;
import com.supermap.data.PixelFormat;
import com.supermap.data.Recordset;
import com.supermap.data.Workspace;

public class ZonalStatisticsTest2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//작업공간을 열고 데이터소스 가져오기
		Workspace workspace = new Workspace();
		Datasources datasources = workspace.getDatasources();
		DatasourceConnectionInfo connectionInfo = new DatasourceConnectionInfo();

		String server = "125.132.237.63:20223";
		String database = "sabang";
		String alias = "PostgreSQL";

		//연결정보 입력하기
		connectionInfo.setEngineType(EngineType.PGGIS);
		connectionInfo.setServer(server);
		connectionInfo.setDatabase(database);
		connectionInfo.setUser("sabangdb");
		connectionInfo.setPassword("podo1234!");
		connectionInfo.setAlias(alias);

//		DatasourceConnectionInfo connectionInfo = new DatasourceConnectionInfo();
//		connectionInfo.setServer("E:/문서/사방협회/DATA/00. DataSource/sabangDataTest.udbx");
//		connectionInfo.setReadOnly(false);
//		connectionInfo.setAlias("sample");
//		connectionInfo.setEngineType(EngineType.UDBX);
		
		Datasource datasource = datasources.open(connectionInfo);
		Datasets datasets = datasource.getDatasets();
		DatasetVector dataset = null;
		Recordset recordset = null;
		String query = "rank = 8597";
		
		String vectorName = "tf_feis_lcp_rank";
		
		if(datasets.contains(vectorName)) {
			dataset = (DatasetVector) datasets.get(vectorName);
			
			recordset = dataset.query(query,CursorType.STATIC);
			
			//DatasetVector datasetVector = (DatasetVector) datasource.copyDataset(dataset, vectorName+"_test", EncodeType.NONE);
			DatasetVector datasetVector = (DatasetVector) datasets.createFromTemplate(datasets.getAvailableDatasetName("rank_test_220829"),dataset);
			
			datasetVector.append(recordset);
			
			datasetVector.close();
			recordset.dispose();
		}
		dataset.close();
		datasource.close();
		connectionInfo.dispose();
		workspace.close();
		workspace.dispose();
		
		System.out.println("finished...");
	}

}
