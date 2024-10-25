//
//  VINController.swift
//  SpeedReporter
//
//  Created by Macbookpro22506 on 24/10/24.
//

import UIKit

class VINController: UIViewController {

    @IBOutlet weak var vinTextField: UITextField!
    @IBOutlet weak var actionButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.vinTextField.becomeFirstResponder()
    }

    // MARK: - UIEvents
    @IBAction func actionTapped(_ sender: UIButton) {
        
        let getVin: String = vinTextField.text!
        if getVin.count > 0 {
            Storage.setBoolData(key: User.isRegistered, value: true)
            Storage.setData(key: User.vin, value: getVin)
            self.performSegue(withIdentifier: "showThresholdScreen", sender: self)
        } else {
            let alert = UIAlertController(title: "Error", message: "Please enter valid Vehicle Number", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "Ok", style: .default))
            self.present(alert, animated: true)
        }
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}




