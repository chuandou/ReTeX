//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/jacky/GSoC/GeoGebraiOSARC/GeoGebraiOSARC/javasources/org/scilab/forge/jlatexmath/parser/NodeI.java
//

#ifndef _NodeI_H_
#define _NodeI_H_

#include "J2ObjC_header.h"
#include "Node.h"

@protocol RXAttr;
@protocol RXElement;
@protocol OrgW3cDomNode;

@interface NodeI : NSObject < RXNode >

#pragma mark Public

- (instancetype)initWithOrgW3cDomNode:(id<OrgW3cDomNode>)impl;

- (id<RXAttr>)castToAttr;

- (id<RXElement>)castToElement;

- (jshort)getNodeType;

@end

J2OBJC_EMPTY_STATIC_INIT(NodeI)

FOUNDATION_EXPORT void NodeI_initWithOrgW3cDomNode_(NodeI *self, id<OrgW3cDomNode> impl);

FOUNDATION_EXPORT NodeI *new_NodeI_initWithOrgW3cDomNode_(id<OrgW3cDomNode> impl) NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(NodeI)

typedef NodeI RXNodeI;

#endif // _NodeI_H_
