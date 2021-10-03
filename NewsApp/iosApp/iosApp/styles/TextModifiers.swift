//
//  TextModifiers.swift
//  TextModifiers
//
//  Created by Anna Zharkova on 09.08.2021.
//

import SwiftUI

struct TextTitle: ViewModifier {
    func body(content: Content) -> some View {
        content.font(Font.system(size: 17).bold())
    }
}

struct SubTitle: ViewModifier {
    func body(content: Content) -> some View {
        content.font(.system(size: 15))
    }
}

struct LargeTitle: ViewModifier {
    func body(content: Content) -> some View {
        content.font(Font.system(size: 22).bold())
    }
}

struct SmallTitle: ViewModifier {
    func body(content: Content) -> some View {
        content.font(.system(size: 12)).foregroundColor(Color.gray)
    }
}

extension View {
    func textTitle() -> some View {
        self.modifier(TextTitle())
    }
    
    func subtextTitle() -> some View {
        self.modifier(SubTitle())
    }
    
    func largeTitle() -> some View {
        self.modifier(LargeTitle())
    }
    
    func smallTitle() -> some View {
        self.modifier(SmallTitle())
    }
}
