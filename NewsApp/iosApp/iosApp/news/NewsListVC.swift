//
//  NewsListVC.swift
//  iosApp
//
//  Created by 1 on 02.06.2020.
//

import UIKit
import app

class NewsListVC: UIViewController {
    private lazy var presenter: NewsPresenter? = {
        let _presenter = NewsPresenter()
        _presenter.attachView(view: self)
        return _presenter
    }()
    
    private var adapter: NewsAdapter? = nil
    @IBOutlet weak var newsList: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.newsList?.register(UINib(nibName: "NewsItemViewCell", bundle: nil), forCellReuseIdentifier: "NewsItemViewCell")
        
      //   DispatchQueue.global(qos: .background).async {
            self.presenter?.loadData()
     //  }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.title = "News List"
        self.newsList?.delegate = adapter
        self.newsList?.dataSource = adapter
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.newsList?.delegate = nil
        self.newsList?.dataSource = nil
        super.viewWillDisappear(animated)
    }
}

extension NewsListVC : INewsListView {
    func setupNews(list: [NewsItem]) {
        if adapter == nil {
            adapter = NewsAdapter()
        }
        self.newsList?.delegate = adapter
        self.newsList?.dataSource = adapter
        adapter?.updateItems(items: list)
        DispatchQueue.main.async {
            self.newsList?.reloadData()
        }
    }
}
