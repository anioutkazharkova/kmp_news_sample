//
//  NewsAdapter.swift
//  iosApp
//
//  Created by 1 on 03.06.2020.
//

import Foundation
import app
import UIKit

class NewsAdapter: NSObject,UITableViewDelegate,UITableViewDataSource {
    
    private var items = [NewsItem]()
    
    func updateItems(items: [NewsItem])
    {
        self.items = [NewsItem]()
        self.items.append(contentsOf: items)
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return items.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let item = items[indexPath.row]
            guard let cell = tableView.dequeueReusableCell(withIdentifier: "NewsItemViewCell", for: indexPath) as? NewsItemViewCell else {
                return UITableViewCell()
            }
            
            cell.setupItem(item: item)
            return cell
    }
    
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
       return 100
    }
    
    func tableView(_ tableView: UITableView, estimatedHeightForRowAt indexPath: IndexPath) -> CGFloat {
        return 100
    }
    
    func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        return 0.0000001
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 0.0000001
    }
}
