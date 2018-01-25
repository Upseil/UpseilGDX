/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.upseil.gdx.box2d.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class DebugRenderer implements Disposable {
    
    public static final Color ShapeInactive = new Color(0.5f, 0.5f, 0.3f, 1);
    public static final Color ShapeStatic = new Color(0.5f, 0.9f, 0.5f, 1);
    public static final Color ShapeKinematic = new Color(0.5f, 0.5f, 0.9f, 1);
    public static final Color ShapeAsleep = new Color(0.6f, 0.6f, 0.6f, 1);
    public static final Color ShapeAwake = new Color(0.9f, 0.7f, 0.7f, 1);
    public static final Color Joint = new Color(0.5f, 0.8f, 0.8f, 1);
    public static final Color AABB = new Color(1.0f, 0f, 1.0f, 1f);
    public static final Color Velocity = new Color(1.0f, 0f, 0f, 1f);
    public static final Color Centroid = new Color(0f, 0f, 1.0f, 1f);
    public static final Color Origin = new Color(1.0f, 0f, 0f, 1f);
    
    public static float OriginLength = 1.0f;
    
    private final static Vector2[] vertices = new Vector2[1000];
    
    private final static Vector2 lower = new Vector2();
    private final static Vector2 upper = new Vector2();
    
    private final static Array<Body> bodies = new Array<Body>();
    private final static Array<Joint> joints = new Array<Joint>();
    
    protected ShapeRenderer renderer;
    
    private boolean drawBodies;
    private boolean drawJoints;
    private boolean drawAABBs;
    private boolean drawInactiveBodies;
    private boolean drawVelocities;
    private boolean drawContacts;
    private boolean drawCentroids;
    private boolean drawOrigins;
    
    public DebugRenderer() {
        this(true, true, false, true, false, true, false, false);
    }
    
    public DebugRenderer(boolean drawBodies, boolean drawJoints, boolean drawAABBs, boolean drawInactiveBodies, boolean drawVelocities, boolean drawContacts,
            boolean drawCentroids, boolean drawOrigins) {
        renderer = new ShapeRenderer();
        
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vector2();
        }
        
        this.drawBodies = drawBodies;
        this.drawJoints = drawJoints;
        this.drawAABBs = drawAABBs;
        this.drawInactiveBodies = drawInactiveBodies;
        this.drawVelocities = drawVelocities;
        this.drawContacts = drawContacts;
        this.drawCentroids = drawCentroids;
        this.drawOrigins = drawOrigins;
    }
    
    /** This assumes that the projection matrix has already been set. */
    public void render(World world, Matrix4 projMatrix) {
        renderer.setProjectionMatrix(projMatrix);
        renderWorld(world);
    }
    
    protected void renderWorld(World world) {
        renderer.begin(ShapeType.Line);
        if (drawBodies || drawAABBs) {
            world.getBodies(bodies);
            for (Body body : bodies) {
                if (body.isActive() || drawInactiveBodies) {
                    renderBody(body);
                }
            }
        }
        if (drawJoints) {
            world.getJoints(joints);
            for (Joint joint : joints) {
                drawJoint(joint);
            }
        }
        renderer.end();
        
        if (drawContacts) {
            renderer.begin(ShapeType.Point);
            for (Contact contact : world.getContactList()) {
                drawContact(contact);
            }
            renderer.end();
        }
    }

    private static final Vector2 originStart = new Vector2();
    private static final Vector2 originEnd = new Vector2();
    
    protected void renderBody(Body body) {
        Transform transform = body.getTransform();
        for (Fixture fixture : body.getFixtureList()) {
            if (drawBodies) {
                drawShape(fixture, transform, getColorByBody(body));
            }
            if (drawAABBs) {
                drawAABB(fixture, transform);
            }
        }
        
        if (drawBodies) {
            Vector2 position = transform.getPosition();
            if (drawVelocities) {
                drawSegment(position, body.getLinearVelocity().add(position), Velocity);
            }
            if (drawOrigins || drawCentroids) {
                float halfOriginLength = OriginLength / 2;
                
                originStart.set(position).sub(halfOriginLength, 0);
                originEnd.set(position).add(halfOriginLength, 0);
                drawSegment(originStart, originEnd, Origin);

                originStart.set(position).sub(0, halfOriginLength);
                originEnd.set(position).add(0, halfOriginLength);
                drawSegment(originStart, originEnd, Origin);
            }
            if (drawCentroids) {
                drawSegment(position, transform.mul(body.getLocalCenter()), Centroid);
            }
        }
    }
    
    protected Color getColorByBody(Body body) {
        if (body.isActive() == false)
            return ShapeInactive;
        else if (body.getType() == BodyType.StaticBody)
            return ShapeStatic;
        else if (body.getType() == BodyType.KinematicBody)
            return ShapeKinematic;
        else if (body.isAwake() == false)
            return ShapeAsleep;
        else
            return ShapeAwake;
    }
    
    protected void drawAABB(Fixture fixture, Transform transform) {
        if (fixture.getType() == Type.Circle) {
            
            CircleShape shape = (CircleShape) fixture.getShape();
            float radius = shape.getRadius();
            vertices[0].set(shape.getPosition());
            transform.mul(vertices[0]);
            lower.set(vertices[0].x - radius, vertices[0].y - radius);
            upper.set(vertices[0].x + radius, vertices[0].y + radius);
            
            // define vertices in ccw fashion...
            vertices[0].set(lower.x, lower.y);
            vertices[1].set(upper.x, lower.y);
            vertices[2].set(upper.x, upper.y);
            vertices[3].set(lower.x, upper.y);
            
            drawSolidPolygon(vertices, 4, AABB, true);
        } else if (fixture.getType() == Type.Polygon) {
            PolygonShape shape = (PolygonShape) fixture.getShape();
            int vertexCount = shape.getVertexCount();
            
            shape.getVertex(0, vertices[0]);
            lower.set(transform.mul(vertices[0]));
            upper.set(lower);
            for (int i = 1; i < vertexCount; i++) {
                shape.getVertex(i, vertices[i]);
                transform.mul(vertices[i]);
                lower.x = Math.min(lower.x, vertices[i].x);
                lower.y = Math.min(lower.y, vertices[i].y);
                upper.x = Math.max(upper.x, vertices[i].x);
                upper.y = Math.max(upper.y, vertices[i].y);
            }
            
            // define vertices in ccw fashion...
            vertices[0].set(lower.x, lower.y);
            vertices[1].set(upper.x, lower.y);
            vertices[2].set(upper.x, upper.y);
            vertices[3].set(lower.x, upper.y);
            
            drawSolidPolygon(vertices, 4, AABB, true);
        }
    }
    
    private static Vector2 t = new Vector2();
    private static Vector2 axis = new Vector2();
    
    protected void drawShape(Fixture fixture, Transform transform, Color color) {
        if (fixture.getType() == Type.Circle) {
            CircleShape circle = (CircleShape) fixture.getShape();
            t.set(circle.getPosition());
            transform.mul(t);
            drawSolidCircle(t, circle.getRadius(), axis.set(transform.vals[Transform.COS], transform.vals[Transform.SIN]), color);
            return;
        }
        
        if (fixture.getType() == Type.Edge) {
            EdgeShape edge = (EdgeShape) fixture.getShape();
            edge.getVertex1(vertices[0]);
            edge.getVertex2(vertices[1]);
            transform.mul(vertices[0]);
            transform.mul(vertices[1]);
            drawSolidPolygon(vertices, 2, color, true);
            return;
        }
        
        if (fixture.getType() == Type.Polygon) {
            PolygonShape chain = (PolygonShape) fixture.getShape();
            int vertexCount = chain.getVertexCount();
            for (int i = 0; i < vertexCount; i++) {
                chain.getVertex(i, vertices[i]);
                transform.mul(vertices[i]);
            }
            drawSolidPolygon(vertices, vertexCount, color, true);
            return;
        }
        
        if (fixture.getType() == Type.Chain) {
            ChainShape chain = (ChainShape) fixture.getShape();
            int vertexCount = chain.getVertexCount();
            for (int i = 0; i < vertexCount; i++) {
                chain.getVertex(i, vertices[i]);
                transform.mul(vertices[i]);
            }
            drawSolidPolygon(vertices, vertexCount, color, false);
        }
    }
    
    private final Vector2 f = new Vector2();
    private final Vector2 v = new Vector2();
    private final Vector2 lv = new Vector2();
    
    protected void drawSolidCircle(Vector2 center, float radius, Vector2 axis, Color color) {
        float angle = 0;
        float angleInc = 2 * (float) Math.PI / 20;
        renderer.setColor(color.r, color.g, color.b, color.a);
        for (int i = 0; i < 20; i++, angle += angleInc) {
            v.set((float) Math.cos(angle) * radius + center.x, (float) Math.sin(angle) * radius + center.y);
            if (i == 0) {
                lv.set(v);
                f.set(v);
                continue;
            }
            renderer.line(lv.x, lv.y, v.x, v.y);
            lv.set(v);
        }
        renderer.line(f.x, f.y, lv.x, lv.y);
        renderer.line(center.x, center.y, 0, center.x + axis.x * radius, center.y + axis.y * radius, 0);
    }
    
    protected void drawSolidPolygon(Vector2[] vertices, int vertexCount, Color color, boolean closed) {
        renderer.setColor(color.r, color.g, color.b, color.a);
        lv.set(vertices[0]);
        f.set(vertices[0]);
        for (int i = 1; i < vertexCount; i++) {
            Vector2 v = vertices[i];
            renderer.line(lv.x, lv.y, v.x, v.y);
            lv.set(v);
        }
        if (closed)
            renderer.line(f.x, f.y, lv.x, lv.y);
    }
    
    protected void drawJoint(Joint joint) {
        Body bodyA = joint.getBodyA();
        Body bodyB = joint.getBodyB();
        Transform xf1 = bodyA.getTransform();
        Transform xf2 = bodyB.getTransform();
        
        Vector2 x1 = xf1.getPosition();
        Vector2 x2 = xf2.getPosition();
        Vector2 p1 = joint.getAnchorA();
        Vector2 p2 = joint.getAnchorB();
        
        if (joint.getType() == JointType.DistanceJoint) {
            drawSegment(p1, p2, Joint);
        } else if (joint.getType() == JointType.PulleyJoint) {
            PulleyJoint pulley = (PulleyJoint) joint;
            Vector2 s1 = pulley.getGroundAnchorA();
            Vector2 s2 = pulley.getGroundAnchorB();
            drawSegment(s1, p1, Joint);
            drawSegment(s2, p2, Joint);
            drawSegment(s1, s2, Joint);
        } else if (joint.getType() == JointType.MouseJoint) {
            drawSegment(joint.getAnchorA(), joint.getAnchorB(), Joint);
        } else {
            drawSegment(x1, p1, Joint);
            drawSegment(p1, p2, Joint);
            drawSegment(x2, p2, Joint);
        }
    }
    
    protected void drawSegment(Vector2 x1, Vector2 x2, Color color) {
        renderer.setColor(color);
        renderer.line(x1.x, x1.y, x2.x, x2.y);
    }
    
    protected void drawContact(Contact contact) {
        WorldManifold worldManifold = contact.getWorldManifold();
        if (worldManifold.getNumberOfContactPoints() == 0)
            return;
        Vector2 point = worldManifold.getPoints()[0];
        renderer.setColor(getColorByBody(contact.getFixtureA().getBody()));
        renderer.point(point.x, point.y, 0);
    }
    
    public boolean isDrawBodies() {
        return drawBodies;
    }
    
    public void setDrawBodies(boolean drawBodies) {
        this.drawBodies = drawBodies;
    }
    
    public boolean isDrawJoints() {
        return drawJoints;
    }
    
    public void setDrawJoints(boolean drawJoints) {
        this.drawJoints = drawJoints;
    }
    
    public boolean isDrawAABBs() {
        return drawAABBs;
    }
    
    public void setDrawAABBs(boolean drawAABBs) {
        this.drawAABBs = drawAABBs;
    }
    
    public boolean isDrawInactiveBodies() {
        return drawInactiveBodies;
    }
    
    public void setDrawInactiveBodies(boolean drawInactiveBodies) {
        this.drawInactiveBodies = drawInactiveBodies;
    }
    
    public boolean isDrawVelocities() {
        return drawVelocities;
    }
    
    public void setDrawVelocities(boolean drawVelocities) {
        this.drawVelocities = drawVelocities;
    }
    
    public boolean isDrawContacts() {
        return drawContacts;
    }
    
    public void setDrawContacts(boolean drawContacts) {
        this.drawContacts = drawContacts;
    }
    
    public boolean isDrawCentroids() {
        return drawCentroids;
    }

    public void setDrawCentroids(boolean drawCentroids) {
        this.drawCentroids = drawCentroids;
    }

    public boolean isDrawOrigins() {
        return drawOrigins;
    }

    public void setDrawOrigins(boolean drawOrigins) {
        this.drawOrigins = drawOrigins;
    }

    public static Vector2 getAxis() {
        return axis;
    }
    
    public static void setAxis(Vector2 axis) {
        DebugRenderer.axis = axis;
    }
    
    public void dispose() {
        renderer.dispose();
    }
}
