import com.jme.math.Vector3f;

import de.unibw.fusionvis.FusionVis;
import de.unibw.fusionvis.implementation.battlesimvis.BattleSimImporter;
import de.unibw.fusionvis.importer.Importer;
import de.unibw.fusionvis.mapper.Mapper;


public class TestMapper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Importer importer = new BattleSimImporter(FusionVis.getLogger());
		Mapper mapper = new Mapper(importer.getDataSet(), new Vector3f(300, 300, 300));
		mapper.getDataRoot();
	}

}
