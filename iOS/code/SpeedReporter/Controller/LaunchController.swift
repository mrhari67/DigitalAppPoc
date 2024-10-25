//
//  ViewController.swift
//  SpeedReporter
//
//  Created by Macbookpro22506 on 23/10/24.
//

import UIKit

class LaunchController: UIViewController {

    @IBOutlet weak var imageLogo: UIImageView!
    
    //let segueToVinController: String = "segueFromLauncher:VinController"

    override func viewDidLoad() {
        super.viewDidLoad()

        self.imageLogo.layer.cornerRadius = 10

        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            let isRegistered: Bool = Storage.getBoolData(key: User.isRegistered)
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let vinController = storyboard.instantiateViewController(identifier: "storyBoardVinController") as VINController
            let thresholdController = storyboard.instantiateViewController(identifier: "storyBoardThresholdController") as ThresholdController
            self.present(isRegistered ? thresholdController : vinController, animated: true)
        }
    }


    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
        /*if segue.identifier == segueToVinController {
            let vin = segue.destination as? VINController
        }*/
    }
}
