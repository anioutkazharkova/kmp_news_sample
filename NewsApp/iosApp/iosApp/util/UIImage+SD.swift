//
//  UIImage+SD.swift
//  iosApp
//
//  Created by 1 on 02.06.2020.
//

import Foundation
import UIKit

extension UIImageView {
    func setupImage(by key: String) {
        weak var weakSelf = self
        ImageStorage.sharedInstance.setupImage(imageKey: key, imageView: weakSelf)
    }
    
    func loadImage(by key: String) {
        weak var weakSelf = self
        ImageStorage.sharedInstance.loadImage(imageKey: key, imageView: weakSelf)
    }
    
    func cleanImage(by key: String) {
        ImageStorage.sharedInstance.cleanImage(key)
        self.image = nil 
    }
}
