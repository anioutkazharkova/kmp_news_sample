//
//  NewsItemViewCell.swift
//  iosApp
//
//  Created by 1 on 03.06.2020.
//

import UIKit
import app

class NewsItemViewCell: UITableViewCell {

    @IBOutlet weak var newsImage: UIImageView!
    
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var titleLabel: UILabel!
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    func setupItem(item: NewsItem) {
        self.newsImage?.setupImage(by: item.urlToImage ?? "")
        self.titleLabel.text = item.title ?? ""
        self.dateLabel.text = item.publishedAt
    }
   
    
}
