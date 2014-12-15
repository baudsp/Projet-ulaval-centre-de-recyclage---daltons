/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recyclapp.serviceTechnique;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import recyclapp.model.Plan;

/**
 *
 * @author Baudouin
 */
public class SavePlan {

    public void save(File selectedFile, Plan plan) throws IOException {

	String path = selectedFile.getAbsolutePath() + ".ser";
	FileOutputStream fout = new FileOutputStream(path);
	ObjectOutputStream oos = new ObjectOutputStream(fout);
	oos.writeObject(plan);
	oos.close();
    }

    public Plan load(File selectedPfile) throws FileNotFoundException, IOException, ClassNotFoundException {
	
	String path = selectedPfile.getAbsolutePath();

	FileInputStream fin = new FileInputStream(path);
	ObjectInputStream ois = new ObjectInputStream(fin);
	Plan plan = new Plan();
	plan = (Plan) ois.readObject();
	ois.close();
	 return plan;
    }

}
