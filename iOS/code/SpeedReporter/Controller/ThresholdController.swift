//
//  ThresholdController.swift
//  SpeedReporter
//
//  Created by Macbookpro22506 on 24/10/24.
//

import UIKit
import MTCircularSlider
import MSCircularSlider
import Alamofire

class ThresholdController: UIViewController {

    @IBOutlet weak var vinNumberLable: UILabel!
    @IBOutlet weak var mapButton: UIButton!

    @IBOutlet weak var speeedSlider: MSCircularSlider!
    
    @IBOutlet weak var speedThresholdLabel: UILabel!
    @IBOutlet weak var currentSpeedLimitLabel: UILabel!
    
    @IBOutlet weak var setThresholdButton: UIButton!
    
    let defaultThresholdValue: Int = 45
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.vinNumberLable.text = Storage.getData(key: User.vin)
        self.currentSpeedLimitLabel.text = ""
        self.speeedSlider.maximumAngle = 360
        
        self.speeedSlider.maximumValue = 150
        self.speeedSlider.minimumValue = 0
        
        self.speeedSlider.delegate = self
        self.setThresholdButton.alpha = 0
    }
    
    @IBAction func mapButtonTapped(_ sender: UIButton) {
        self.performSegue(withIdentifier: "showMapView", sender: self)
    }
    
    @IBAction func setThresholdButtonTapped(_ sender: UIButton) {

        let vehNum = UserDefaults.standard.object(forKey: User.vin) as! String
        let params = ThresholdProp(vin: vehNum, speedLimit: Int(speeedSlider.currentValue))
        AF.request(RestApi.EndPoint.SetSpeedThreshold,
                   method: .post,
                   parameters: params,
                   encoder: JSONParameterEncoder.default)
        .response { response in
            //debugPrint(response)
            switch response.result {
                case let .success(value):
                print(value as Any)
                case let .failure(error):
                    print(error)
            }
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

extension ThresholdController: MSCircularSliderDelegate {

    func circularSlider(_ slider: MSCircularSlider, valueChangedTo value: Double, fromUser: Bool) {
        if defaultThresholdValue != Int(value) {
            self.setThresholdButton.alpha = 1
        } else {
            self.setThresholdButton.alpha = 0
        }
        self.speedThresholdLabel.text = String(format: "%.0f", value)
    }
}

struct ThresholdProp: Encodable {
    let vin: String
    let speedLimit: Int
}
